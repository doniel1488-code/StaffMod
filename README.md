# Governix Staff Companion

A client-side Fabric mod that provides a custom staff dashboard for the Governix Minecraft server.

The mod is a client companion only. Staff permissions, anti-cheat decisions, punishments, checks, and command results remain controlled by the server and its installed plugins.

## Supported Minecraft versions

| Minecraft version | Build module | Runtime Java |
| --- | --- | ---: |
| 1.21.11 | `mc12111` | 21 |
| 26.1.2 | `mc2612` | 25 |
| 26.2 | `mc262` | 25 |
| 26.3 | `mc263` | 25 |

Each Minecraft version has its own build and output JAR. **Do not install a JAR built for a different Minecraft version.** The versions listed above have passed the corresponding GitHub Actions build. In-game testing on every target version has not yet been completed.

## Features

- Open the staff dashboard with **Right Shift**. The key can be rebound in Minecraft's Controls settings.
- A custom-drawn interface with no vanilla inventory/container screen, stock button widgets, or stock text-field widgets.
- Separate sections for overview, anti-cheat tools, player checks, player tools, and Staff Chat.
- Russian and English keybind names.
- Player-name validation before player-targeted commands are sent.
- The client reports that a command was sent; it does not invent or simulate a successful server response.

## Requirements

- Fabric Loader for the exact Minecraft version you use.
- Fabric API for that same Minecraft version.
- Java 21 for Minecraft 1.21.11, or Java 25 for Minecraft 26.x.

The 1.21.11 build is compiled for Java 21 bytecode. Its GitHub Actions build uses JDK 25 to run the current Gradle/Fabric Loom toolchain.

## Installation

1. Install Fabric Loader for your Minecraft version.
2. Install the matching Fabric API release.
3. Download the `GovernixStaffMod` JAR built for your exact Minecraft version.
4. Put the JAR in your Minecraft `mods` folder.
5. Start the Fabric profile and join a server where you have the required staff permissions.
6. Press **Right Shift** to open the dashboard.

Do not put the Paper server plugin JAR in the client `mods` folder. This repository contains the client mod source and builds only.

## Governix server integration

The dashboard sends existing commands to the connected server. The server is authoritative and must have the corresponding command handlers installed. The current UI references these commands:

| Purpose | Command |
| --- | --- |
| Anti-cheat alerts | `/gov alerts` |
| Verbose output | `/gov verbose` |
| Anti-cheat version/status | `/gov version` |
| AI status | `/gov ai status` |
| Player anti-cheat profile | `/gov profile <player>` |
| Player modification report | `/gov mods <player>` |
| Start a player check | `/check <player>` |
| Repeat AnyDesk instructions | `/check anydesk <player>` |
| Finish a player check | `/check finish <player>` |
| Open the check verdict flow | `/checkban <player>` |
| Spectate a player / stop spectating | `/spec <player>` / `/spec` |
| Staff Chat | `/sc <message>` |

The availability, syntax, and permission requirements of these commands depend on the server-side plugin versions and configuration. A command can be rejected by the server. The client does not bypass permissions or enforcement.

## Build from source

Use JDK 25 to run Gradle and the Fabric Loom plugin. The 1.21.11 module separately targets Java 21.

Build an individual target:

```bash
gradle :mc12111:build
gradle :mc2612:build
gradle :mc262:build
gradle :mc263:build
```

Or build every module:

```bash
gradle build
```

The resulting JAR files are written to each module's `build/libs/` directory.

## Continuous integration

GitHub Actions builds each supported Minecraft target independently and uploads a separate artifact:

- `GovernixStaffMod-1.21.11`
- `GovernixStaffMod-26.1.2`
- `GovernixStaffMod-26.2`
- `GovernixStaffMod-26.3`

See [GitHub Actions](https://github.com/doniel1488-code/StaffMod/actions) for build logs and artifacts.

## Project layout

- `mc12111/` — Minecraft 1.21.11 client source.
- `mc2612/` — Minecraft 26.1.2 client source.
- `mc262/` — Minecraft 26.2 client source.
- `mc263/` — Minecraft 26.3 client source.
- `.github/workflows/build.yml` — version-specific CI builds.

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for the full text.
