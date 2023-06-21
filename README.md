# OpenTodayTelemetry
Telemetry (Crash reporter) for OpenToday project

subproject for https://github.com/fazziclay/opentoday


# Protocol
(for packet-sending data using NeoSocket library.)

See NeoSocket README before...

## Handshake
1. client sent `PacketSetVersion(=0)`
2. client sent `Packet20004Login`(contains instanceId UUID)
3. client sent `Packet20005Handshake`(application build)
4. (optional) client sent `Packet20010NotifyDebugUser` (mark client as debug-app-build)