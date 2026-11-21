# Governix Staff Companion

A client-side Fabric staff dashboard for the Governix Minecraft server. This repository contains the mod source only; the server-side GovernixAC Paper plugin is maintained and installed separately.

## Compatibility builds

| Minecraft | Module | Java |
| --- | --- | ---: |
| 1.21.11 | mc12111 | 21 |
| 26.3.x | mc263 | 25 |

Each line is compiled separately. A JAR compiled for one line is not advertised as compatible with the other. This initial 26.x target is 26.3.x; 26.1 and 26.2 need their own compile and runtime verification before being listed as supported.

## Interface

- Open the panel with Right Shift (rebindable in Minecraft Controls).
- The 26.3 dashboard is custom-drawn: no inventory/container screen, fake server menu, stock buttons or stock text fields.
- Pages: overview, anti-cheat, checks, player tools and Staff Chat.
- Russian and English keybind labels.

## GovernixAC integration contract

The client sends existing commands to the connected Paper server. The server plugin remains authoritative for permissions, staff hierarchy, enforcement and command results. The client does not grant permissions, bypass checks, generate anti-cheat verdicts, or pretend an action succeeded.

Commands referenced by the UI:
- /gov alerts, /gov verbose, /gov version, /gov ai status
- /gov profile <player>, /gov mods <player>
- /check <player>, /check anydesk <player>, /check finish <player>, /checkban <player>
- /spec <player>, /spec
- /sc <message>

A command may be denied by the server or may differ on another plugin version. The UI reports only that a command was sent; the server response is the source of truth.

## Build

- gradle :mc12111:build
- gradle :mc263:build

GitHub Actions builds both targets independently and uploads separate JAR artifacts. No plugin JAR is stored in this repository.
