# Governix Staff Companion

Client-side Fabric companion for the **GovernixAC** Paper plugin supplied in this repository.

## Scope

The companion provides a fully custom client screen (no vanilla inventory/container GUI) for staff workflows. Actions are sent as ordinary server commands so the Paper plugin remains the authority for permissions and enforcement.

Current plugin-backed actions discovered in GovernixAC 1.2.7:
- AC alerts: `/gov alerts`
- Verbose: `/gov verbose`
- Player profile: `/gov profile <player>`
- Spectate: `/spec <player>` (toggle off with `/spec`)
- Start / finish check: `/check <player>`, `/check finish <player>`
- Check reminder: `/check anydesk <player>`
- Ban verdict screen on the server: `/checkban <player>`
- Client-mod report: `/gov mods <player>`
- AC diagnostics: `/gov version`, `/gov ai status`
- Staff chat: `/sc <message>`

The client does not grant permissions, bypass server checks, or make anti-cheat decisions. A button can still be rejected by the server if the player lacks the relevant permission.

## Build

The first implementation targets Fabric + Minecraft 1.21.11. Run:

```sh
gradle :mc12111:build
```

The 26.1+ line is a separate compatibility target because Minecraft 26.1 is unobfuscated and Fabric's mappings/tooling changed substantially. It will be added as a separately compiled module; a 1.21.11 JAR must not be advertised as compatible with 26.1.

## Controls

Default key: **Right Shift**. Rebind it in Minecraft Controls under the Governix category.
