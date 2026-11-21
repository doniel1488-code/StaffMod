package ru.governix.staffmod;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Entirely client-rendered dashboard. It intentionally does not use a vanilla
 * inventory screen, container, slot grid, or server-side fake menu.
 */
public final class StaffDashboardScreen extends Screen {
    private static final int BG = 0xF20B1019;
    private static final int PANEL = 0xFF121B29;
    private static final int PANEL_ALT = 0xFF172335;
    private static final int ACCENT = 0xFF8B7CFF;
    private static final int MUTED = 0xFF9BAAC0;

    private final List<ButtonWidget> navButtons = new ArrayList<>();
    private TextFieldWidget playerField;
    private TextFieldWidget chatField;
    private String activePage = "overview";
    private String status = "Команды выполняются сервером и проверяются правами Paper.";

    public StaffDashboardScreen() {
        super(Text.literal("GOVERNIX // STAFF CONSOLE"));
    }

    @Override
    protected void init() {
        clearChildren();
        navButtons.clear();

        int navX = 18;
        int navY = 78;
        String[][] pages = {
                {"overview", "Обзор"},
                {"anticheat", "Античит"},
                {"checks", "Проверки"},
                {"player", "Игрок"},
                {"staff", "Staff Chat"}
        };
        for (String[] page : pages) {
            ButtonWidget button = ButtonWidget.builder(Text.literal(page[1]), b -> {
                activePage = page[0];
                init();
            }).dimensions(navX, navY, 112, 22).build();
            navButtons.add(button);
            addDrawableChild(button);
            navY += 27;
        }

        int contentX = 152;
        int contentW = Math.max(180, width - contentX - 20);
        if (activePage.equals("player") || activePage.equals("checks")) {
            playerField = new TextFieldWidget(textRenderer, contentX, 111, Math.min(250, contentW), 22, Text.literal("Ник игрока"));
            playerField.setMaxLength(16);
            playerField.setPlaceholder(Text.literal("Ник игрока"));
            addDrawableChild(playerField);
        }
        if (activePage.equals("staff")) {
            chatField = new TextFieldWidget(textRenderer, contentX, 111, Math.min(320, contentW), 22, Text.literal("Сообщение Staff Chat"));
            chatField.setMaxLength(180);
            chatField.setPlaceholder(Text.literal("Сообщение в Staff Chat"));
            addDrawableChild(chatField);
        }

        switch (activePage) {
            case "overview" -> addAction(contentX, 112, contentW, "Открыть профиль /gov version", "/gov version");
            case "anticheat" -> {
                addAction(contentX, 112, contentW, "Переключить AC Alerts", "/gov alerts");
                addAction(contentX, 140, contentW, "Переключить Verbose", "/gov verbose");
                addAction(contentX, 168, contentW, "Версия и состояние AC", "/gov version");
                addAction(contentX, 196, contentW, "Диагностика AI", "/gov ai status");
            }
            case "checks" -> {
                addAction(contentX, 142, contentW, "Вызвать на проверку", "/check <player>");
                addAction(contentX, 170, contentW, "Повторить инструкцию AnyDesk", "/check anydesk <player>");
                addAction(contentX, 198, contentW, "Завершить проверку", "/check finish <player>");
                addAction(contentX, 226, contentW, "Открыть серверный вердикт", "/checkban <player>");
            }
            case "player" -> {
                addAction(contentX, 142, contentW, "Профиль античита", "/gov profile <player>");
                addAction(contentX, 170, contentW, "Скрытое наблюдение", "/spec <player>");
                addAction(contentX, 198, contentW, "Отчёт о модификациях", "/gov mods <player>");
            }
            case "staff" -> addAction(contentX, 142, contentW, "Отправить в Staff Chat", "/sc <message>");
        }
    }

    private String playerToken() {
        return playerField == null ? "" : playerField.getText().trim();
    }

    private String chatToken() {
        return chatField == null ? "" : chatField.getText().trim();
    }

    private void addAction(int x, int y, int w, String label, String command) {
        addDrawableChild(ButtonWidget.builder(Text.literal(label), button -> {
            String player = playerToken();
            String message = chatToken();
            if (command.contains("<player>") && !player.matches("[A-Za-z0-9_]{3,16}")) {
                status = "Введите корректный ник (3–16 символов).";
                return;
            }
            if (command.contains("<message>") && message.isBlank()) {
                status = "Введите сообщение для Staff Chat.";
                return;
            }
            String actual = command.replace("<player>", player).replace("<message>", message);
            GovernixStaffClient.runServerCommand(actual);
            status = "Команда отправлена серверу. Результат проверки покажет сервер.";
        }).dimensions(x, y, w, 22).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        context.fill(0, 0, width, height, BG);
        context.fill(12, 12, width - 12, 57, PANEL);
        context.fill(12, 66, 140, height - 12, PANEL);
        context.fill(148, 66, width - 12, height - 12, PANEL_ALT);

        context.drawText(textRenderer, "GOVERNIX", 27, 23, 0xFFFFFFFF, true);
        context.drawText(textRenderer, "STAFF CONTROL / CLIENT CONSOLE", 120, 25, ACCENT, false);
        context.drawText(textRenderer, "CUSTOM INTERFACE  •  SERVER-AUTHORISED ACTIONS", 27, 42, MUTED, false);

        context.drawText(textRenderer, activeTitle(), 164, 82, 0xFFFFFFFF, true);
        context.drawText(textRenderer, "Страница использует команды GovernixAC 1.2.7", 164, 96, MUTED, false);

        if (activePage.equals("checks") || activePage.equals("player")) {
            context.drawText(textRenderer, "ИГРОК", 164, 101, MUTED, false);
        }
        if (activePage.equals("staff")) {
            context.drawText(textRenderer, "СООБЩЕНИЕ", 164, 101, MUTED, false);
        }

        super.render(context, mouseX, mouseY, delta);
        context.fill(160, height - 39, width - 24, height - 22, 0xAA0A111B);
        context.drawText(textRenderer, trim(status, Math.max(20, (width - 210) / 6)), 168, height - 34, MUTED, false);
        context.drawText(textRenderer, "RIGHT SHIFT  •  ESC ЗАКРЫТЬ", 20, height - 25, MUTED, false);
    }

    private String activeTitle() {
        return switch (activePage) {
            case "anticheat" -> "АНТИЧИТ / ДИАГНОСТИКА";
            case "checks" -> "ПРОВЕРКА ИГРОКА";
            case "player" -> "ИНСТРУМЕНТЫ ИГРОКА";
            case "staff" -> "STAFF COMMUNICATION";
            default -> "ОБЗОР СИСТЕМЫ";
        };
    }

    private String trim(String value, int max) {
        if (value.length() <= max) return value;
        return value.substring(0, Math.max(0, max - 1)) + "…";
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
