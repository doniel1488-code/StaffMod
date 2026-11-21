# Governix Staff Companion

A client-side Fabric staff dashboard for the Governix Minecraft server. This repository contains the mod source only; the server-side GovernixAC Paper plugin is maintained and installed separately.

## Compatibility builds

| Minecraft | Module | Java |
| --- | --- | ---: |
| 1.21.11 | mc12111 | 21 |
| 26.1.2 | mc2612 | 25 |
| 26.2 | mc262 | 25 |
| 26.3.x | mc263 | 25 |

Each release line has a separate compiled module. A JAR compiled for one Minecraft line is not advertised as compatible with another. The listed versions are the versions actually built by CI; additional point releases are added only after a compile check.

## Interface

- Open the panel with Right Shift (rebindable in Minecraft Controls).
- Both the 1.21.11 and 26.x dashboards are custom-drawn; no inventory/container screen or stock button/text-field widgets are used.
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
- gradle :mc2612:build
- gradle :mc262:build
- gradle :mc263:build

GitHub Actions builds each target independently and uploads separate JAR artifacts. No server plugin JAR is stored in this repository.
