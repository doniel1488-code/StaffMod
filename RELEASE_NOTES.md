<!-- GOVERNIX RELEASE HEADER -->
<p align="center">
  <strong>GOVERNIX</strong><br/>
  <strong>STAFF COMPANION</strong><br/>
  <sub>Клиентская панель персонала · Fabric · Только для проекта Governix</sub>
</p>

<p align="center">
  <img alt="Версия 0.2.1" src="https://img.shields.io/badge/VERSION-0.2.1-8B7CFF?style=for-the-badge"/>
  <img alt="Fabric" src="https://img.shields.io/badge/LOADER-FABRIC-E6B85C?style=for-the-badge"/>
  <img alt="Исправление краша" src="https://img.shields.io/badge/UPDATE-CRASH%20FIX-37B879?style=for-the-badge"/>
</p>

---

## ✦ Что нового в 0.2.1

**Исправлен вылет клиента при открытии панели персонала в Minecraft 1.21.11.**

Причина — повторный вызов размытия фона в одном кадре (<code>Can only blur once per frame</code>). Панель уже рисует собственный фон, поэтому лишний вызов ванильного blur удалён.

Интерфейс, разделы, ввод текста и отправка серверных команд не менялись.

## ↓ Скачать

Выбери файл строго под свою версию Minecraft. **Не устанавливай JAR от другой версии игры.**

| Minecraft | Java при запуске | Файл |
|:--|:--:|:--|
| **1.21.11** | 21 | [Скачать JAR](https://github.com/doniel1488-code/StaffMod/releases/download/v0.2.1/GovernixStaffMod-1.21.11-0.2.1.jar) |
| **26.1.2** | 25 | [Скачать JAR](https://github.com/doniel1488-code/StaffMod/releases/download/v0.2.1/GovernixStaffMod-26.1.2-0.2.1.jar) |
| **26.2** | 25 | [Скачать JAR](https://github.com/doniel1488-code/StaffMod/releases/download/v0.2.1/GovernixStaffMod-26.2-0.2.1.jar) |
| **26.3** | 25 | [Скачать JAR](https://github.com/doniel1488-code/StaffMod/releases/download/v0.2.1/GovernixStaffMod-26.3-0.2.1.jar) |

<p align="center">
  <a href="https://github.com/doniel1488-code/StaffMod/releases/download/v0.2.1/SHA256SUMS.txt"><strong>Проверить SHA-256 всех файлов</strong></a>
  &nbsp;·&nbsp;
  <a href="https://github.com/doniel1488-code/StaffMod"><strong>Исходный код</strong></a>
  &nbsp;·&nbsp;
  <a href="https://github.com/doniel1488-code/StaffMod/actions"><strong>Сборки CI</strong></a>
</p>

## Обновление

1. Полностью закрой Minecraft.
2. Открой папку <code>.minecraft/mods</code>.
3. Удали старый <code>GovernixStaffMod-...-0.2.0.jar</code>.
4. Помести в папку новый JAR для своей версии игры.
5. Запусти Fabric и открой панель клавишей **Right Shift**.

Не оставляй старую и новую версии мода одновременно.

## Совместимость и проверка

- **Сборка:** все четыре варианта собраны GitHub Actions.
- **Пакеты:** JAR-файлы и файл контрольных сумм загружены в этот релиз.
- **Ручной запуск игры:** отдельно не проверялся. После установки проверь открытие панели в своём клиенте.

[Открыть успешную сборку и публикацию v0.2.1](https://github.com/doniel1488-code/StaffMod/actions/runs/44895521400)

## Условия использования

Мод создан исключительно для официального Minecraft-проекта **Governix**. Использование на сторонних серверах, в других сетях или проектах запрещено без предварительного письменного разрешения правообладателя.

[Читать полную лицензию Governix Staff Companion](https://github.com/doniel1488-code/StaffMod/blob/main/LICENSE).

---

<p align="center">
  <sub>GOVERNIX · STAFF COMPANION · RELEASE 0.2.1</sub>
</p>
