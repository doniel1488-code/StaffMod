# Governix Staff Companion v0.2.0

Initial public multi-version release of the Governix Staff Companion client mod.

## Downloads

Choose the JAR that matches your exact Minecraft version:

- **Minecraft 1.21.11** — Java 21 runtime
- **Minecraft 26.1.2** — Java 25 runtime
- **Minecraft 26.2** — Java 25 runtime
- **Minecraft 26.3** — Java 25 runtime

Each JAR is built separately. Do not use a JAR from a different Minecraft version.

## Included

- Custom-drawn staff dashboard opened with Right Shift.
- Overview, anti-cheat, player-check, player-tools, and Staff Chat sections.
- Russian and English keybind names.
- Player-name validation for player-targeted commands.
- Server-authoritative command handling: the client does not bypass staff permissions or claim that a command succeeded before the server responds.

## Installation

1. Install Fabric Loader for your Minecraft version.
2. Install the matching Fabric API.
3. Download the matching Governix Staff Companion JAR from this release.
4. Place the JAR in the Minecraft `mods` folder.
5. Launch the matching Fabric profile and press **Right Shift** in-game.

## Compatibility and verification

All four release targets passed their separate GitHub Actions build and JAR packaging jobs. Manual in-game testing on every target version has not yet been completed. The server must also provide the commands listed in the README and grant the required permissions.

The project is distributed under the MIT License.
