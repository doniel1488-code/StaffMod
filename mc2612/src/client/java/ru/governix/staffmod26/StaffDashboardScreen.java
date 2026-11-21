package ru.governix.staffmod26;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

import java.util.List;

/**
 * Custom-drawn staff dashboard. No vanilla inventory, container, slot menu,
 * or stock button/text-field widgets are used.
 */
public final class StaffDashboardScreen extends Screen {
    private static final int BACKDROP = 0xD9070C14;
    private static final int FRAME = 0xFF0D1622;
    private static final int HEADER = 0xFF111E2D;
    private static final int SIDEBAR = 0xFF101A27;
    private static final int CARD = 0xFF172638;
    private static final int CARD_HOVER = 0xFF20354B;
    private static final int BORDER = 0xFF2B4056;
    private static final int ACCENT = 0xFF8B7CFF;
    private static final int TEXT = 0xFFF1F5FC;
    private static final int MUTED = 0xFF9AABC0;
    private static final int RED = 0xFFFF7777;

    private static final String[] PAGE_LABELS = {"Обзор", "Античит", "Проверки", "Игрок", "Staff Chat"};

    private int page;
    private int frameX, frameY, frameW, frameH, sidebarW, contentX, contentW, inputY, actionStartY;
    private String input = "";
    private boolean inputFocused;
    private String status = "Действия отправляются серверу. Права и результат определяет Paper.";
    private boolean statusError;

    private record Action(String label, String command, boolean needsPlayer, boolean needsMessage) {}

    public StaffDashboardScreen() {
        super(Component.literal("GOVERNIX STAFF CONSOLE"));
    }

    @Override
    protected void init() {
        frameW = Math.min(920, Math.max(320, width - 24));
        frameH = Math.min(540, Math.max(240, height - 24));
        frameX = (width - frameW) / 2;
        frameY = (height - frameH) / 2;
        sidebarW = frameW < 560 ? 116 : 164;
        contentX = frameX + sidebarW + 22;
        contentW = Math.max(150, frameW - sidebarW - 44);
        inputY = frameY + 126;
        actionStartY = frameY + (usesInput() ? 172 : 132);
    }

    private boolean usesInput() {
        return page == 2 || page == 3 || page == 4;
    }

    private List<Action> actions() {
        return switch (page) {
            case 1 -> List.of(
                    new Action("Переключить AC Alerts", "gov alerts", false, false),
                    new Action("Переключить Verbose", "gov verbose", false, false),
                    new Action("Версия и состояние AC", "gov version", false, false),
                    new Action("Диагностика AI", "gov ai status", false, false)
            );
            case 2 -> List.of(
                    new Action("Вызвать на проверку", "check", true, false),
                    new Action("Повторить инструкцию AnyDesk", "check anydesk", true, false),
                    new Action("Завершить проверку", "check finish", true, false),
                    new Action("Открыть серверный вердикт", "checkban", true, false)
            );
            case 3 -> List.of(
                    new Action("Профиль античита", "gov profile", true, false),
                    new Action("Начать наблюдение", "spec", true, false),
                    new Action("Остановить наблюдение", "spec", false, false),
                    new Action("Отчёт о модификациях", "gov mods", true, false)
            );
            case 4 -> List.of(new Action("Отправить сообщение", "sc", false, true));
            default -> List.of(
                    new Action("Версия GovernixAC", "gov version", false, false),
                    new Action("Состояние AI", "gov ai status", false, false),
                    new Action("Состояние AC Alerts", "gov alerts", false, false)
            );
        };
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        super.extractRenderState(graphics, mouseX, mouseY, delta);
        graphics.fill(0, 0, width, height, BACKDROP);
        graphics.fill(frameX, frameY, frameX + frameW, frameY + frameH, FRAME);
        graphics.outline(frameX, frameY, frameW, frameH, BORDER);
        graphics.fill(frameX, frameY, frameX + frameW, frameY + 58, HEADER);
        graphics.fill(frameX + 1, frameY + 58, frameX + sidebarW, frameY + frameH - 1, SIDEBAR);

        graphics.text(font, "GOVERNIX", frameX + 20, frameY + 16, TEXT, true);
        graphics.text(font, "STAFF CONTROL", frameX + 106, frameY + 18, ACCENT, true);
        graphics.text(font, "CUSTOM CLIENT CONSOLE / PAPER-AUTHORISED ACTIONS", frameX + 20, frameY + 39, MUTED, false);

        for (int i = 0; i < PAGE_LABELS.length; i++) {
            int y = frameY + 73 + i * 34;
            boolean selected = page == i;
            boolean hovered = inside(mouseX, mouseY, frameX + 12, y, sidebarW - 24, 28);
            graphics.fill(frameX + 12, y, frameX + sidebarW - 12, y + 28,
                    selected ? CARD_HOVER : (hovered ? 0xFF182637 : SIDEBAR));
            if (selected) graphics.fill(frameX + 12, y, frameX + 15, y + 28, ACCENT);
            graphics.text(font, PAGE_LABELS[i], frameX + 24, y + 10, selected ? TEXT : MUTED, selected);
        }

        graphics.text(font, PAGE_LABELS[page].toUpperCase(), contentX, frameY + 80, TEXT, true);
        graphics.text(font, pageDescription(), contentX, frameY + 98, MUTED, false);

        if (usesInput()) {
            graphics.text(font, page == 4 ? "СООБЩЕНИЕ STAFF CHAT" : "НИК ИГРОКА", contentX, frameY + 116, MUTED, false);
            int fieldW = Math.min(contentW, 360);
            graphics.fill(contentX, inputY, contentX + fieldW, inputY + 28, 0xFF0A121C);
            graphics.outline(contentX, inputY, fieldW, 28, inputFocused ? ACCENT : BORDER);
            String shown = input.isEmpty() ? (page == 4 ? "Введите сообщение..." : "Введите Minecraft-ник") : input;
            graphics.text(font, font.plainSubstrByWidth(shown, Math.max(12, fieldW - 18)),
                    contentX + 9, inputY + 9, input.isEmpty() ? 0xFF71849A : TEXT, false);
        }

        List<Action> currentActions = actions();
        for (int i = 0; i < currentActions.size(); i++) {
            Action action = currentActions.get(i);
            int y = actionStartY + i * 36;
            boolean hovered = inside(mouseX, mouseY, contentX, y, contentW, 29);
            graphics.fill(contentX, y, contentX + contentW, y + 29, hovered ? CARD_HOVER : CARD);
            graphics.outline(contentX, y, contentW, 29, hovered ? ACCENT : BORDER);
            graphics.text(font, action.label(), contentX + 10, y + 10, TEXT, false);
            graphics.text(font, ">", contentX + contentW - 15, y + 10, ACCENT, true);
        }

        int footerY = frameY + frameH - 42;
        graphics.fill(frameX + 12, footerY, frameX + frameW - 12, footerY + 26, 0xFF0A121C);
        graphics.text(font, font.plainSubstrByWidth(status, Math.max(18, frameW - 200)),
                frameX + 20, footerY + 9, statusError ? RED : MUTED, false);
        graphics.text(font, "RIGHT SHIFT  /  ESC", frameX + frameW - 116, footerY + 9, MUTED, false);
    }

    private String pageDescription() {
        return switch (page) {
            case 1 -> "Управление оповещениями и диагностикой античита";
            case 2 -> "Начало и завершение серверной процедуры проверки";
            case 3 -> "Профили, наблюдение и сведения о клиенте игрока";
            case 4 -> "Сообщения в защищённый Staff Chat";
            default -> "Быстрый доступ к инструментам GovernixAC";
        };
    }

    private boolean inside(double px, double py, int x, int y, int w, int h) {
        return px >= x && px <= x + w && py >= y && py <= y + h;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (event.button() != InputConstants.MOUSE_BUTTON_LEFT) return super.mouseClicked(event, doubleClick);
        double mx = event.x();
        double my = event.y();

        for (int i = 0; i < PAGE_LABELS.length; i++) {
            int y = frameY + 73 + i * 34;
            if (inside(mx, my, frameX + 12, y, sidebarW - 24, 28)) {
                page = i;
                input = "";
                inputFocused = false;
                actionStartY = frameY + (usesInput() ? 172 : 132);
                status = "Раздел открыт. Команды по-прежнему проверяются сервером.";
                statusError = false;
                rebuildWidgets();
                return true;
            }
        }

        if (usesInput() && inside(mx, my, contentX, inputY, Math.min(contentW, 360), 28)) {
            inputFocused = true;
            return true;
        }
        inputFocused = false;

        List<Action> currentActions = actions();
        for (int i = 0; i < currentActions.size(); i++) {
            int y = actionStartY + i * 36;
            if (inside(mx, my, contentX, y, contentW, 29)) {
                execute(currentActions.get(i));
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        int key = event.key();
        if (key == InputConstants.KEY_ESCAPE) {
            onClose();
            return true;
        }
        if (inputFocused && key == InputConstants.KEY_BACKSPACE) {
            if (!input.isEmpty()) {
                int end = input.offsetByCodePoints(input.length(), -1);
                input = input.substring(0, end);
            }
            return true;
        }
        return super.keyPressed(event);
    }

    @Override
    public boolean charTyped(CharacterEvent event) {
        if (!inputFocused || !event.isAllowedChatCharacter()) return super.charTyped(event);
        String addition = event.codepointAsString();
        int max = page == 4 ? 180 : 16;
        if (input.length() + addition.length() > max) return true;
        String candidate = input + addition;
        if (page != 4 && !candidate.matches("[A-Za-z0-9_]*")) return true;
        if (page == 4 && (addition.contains("\n") || addition.contains("\r"))) return true;
        input = candidate;
        return true;
    }

    private void execute(Action action) {
        String command;
        if (action.needsPlayer()) {
            if (!input.matches("[A-Za-z0-9_]{3,16}")) {
                setStatus("Введите корректный ник: 3–16 символов A–Z, a–z, 0–9 или _.", true);
                return;
            }
            command = switch (action.command()) {
                case "check" -> "check " + input;
                case "check anydesk" -> "check anydesk " + input;
                case "check finish" -> "check finish " + input;
                case "checkban" -> "checkban " + input;
                default -> action.command() + " " + input;
            };
        } else if (action.needsMessage()) {
            String message = input.trim().replaceAll("[\\p{Cntrl}&&[^\\t]]", " ");
            if (message.isBlank()) {
                setStatus("Введите сообщение для Staff Chat.", true);
                return;
            }
            command = "sc " + message;
        } else {
            command = action.command();
        }

        Minecraft client = Minecraft.getInstance();
        if (client.getConnection() == null || client.player == null) {
            setStatus("Нет активного подключения к серверу.", true);
            return;
        }
        client.getConnection().sendCommand(command);
        setStatus("Команда отправлена. Дождитесь ответа сервера.", false);
    }

    private void setStatus(String message, boolean error) {
        status = message;
        statusError = error;
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(null);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
