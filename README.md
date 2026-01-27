![BetterIPFILTER](src/main/resources/betteripfilter-logo.png)

![Java Version](https://img.shields.io/badge/Java-21+-blue)
![PaperMC](https://img.shields.io/badge/Paper-1.21.x-white)
![Release](https://img.shields.io/github/v/release/AREKKUZZERA/better-IP-Filter?style=flat-square&logo=github)
[![Modrinth](https://img.shields.io/badge/Modrinth-Available-1bd96a?logo=modrinth&logoColor=white)](https://modrinth.com/plugin/better-ip-filter)

**Better-IP-Filter** is a lightweight and fast IP whitelist plugin for **Minecraft Paper servers**.  
It performs early IP validation during the login process and blocks connections from non-whitelisted IP addresses with minimal performance impact.

---

## ✨ Features

- IP whitelist filtering on player join  
- Uses `AsyncPlayerPreLoginEvent` (early, efficient check)  
- Toggleable filtering without server restart  
- Extremely lightweight (O(1) lookups)  
- No external dependencies  
- IPv4 validation (exact, CIDR, and ranges)  
- Persistent storage (`ips.yml`)  
- Proxy trusted-forwarded IP gate (no header parsing)  
- Optional rate limiting and failsafe behavior  
- Optional webhook notifications  
- Fully compatible with LuckPerms (Bukkit permissions)

---

## 📦 Requirements

- **Java:** 21 or newer  
- **Server:** Paper 1.21 - 1.21.11  
- **Build tool:** Maven (only if building from source)

---

## 📥 Installation

1. Download the compiled `Better-IP-Filter.jar`
2. Place it into your server’s `plugins/` directory
3. Start the server once to generate configuration files
4. Edit `config.yml` if needed
5. Restart the server

---

## ⚙️ Configuration

The plugin configuration is located in `config.yml`.

### Example `config.yml`

```yml
enabled: true

messages:
  prefix: "&7[&bBetter-IP-Filter&7] &r"
  notAllowed: "&cYour IP address is not allowed on this server."
  enabled: "&aIP filtering enabled."
  disabled: "&eIP filtering disabled."
  added: "&aAdded IP: &f{ip}"
  alreadyExists: "&eIP already exists: &f{ip}"
  removed: "&aRemoved IP: &f{ip}"
  notFound: "&cIP not found: &f{ip}"
  failedUpdate: "&cFailed to update whitelist."
  storeUnavailable: "&cWhitelist storage unavailable."
  invalidIp: "&cInvalid IP address: &f{ip}"
  listHeader: "&bWhitelisted entries &7({count})&b:"
  noPermission: "&cYou do not have permission to do that."
  reloaded: "&aConfiguration reloaded."
  statusHeader: "&bBetter-IP-Filter status:"
  proxyNotTrusted: "&cConnection denied: proxy is not trusted."

proxy:
  mode: "DIRECT" # DIRECT | BUNGEE | VELOCITY
  trusted-forwarded-ips: []

ratelimit:
  enabled: true
  window-seconds: 10
  max-attempts: 5
  message: "&cToo many connection attempts. Try again later."

failsafe:
  mode: "DENY_ALL" # DENY_ALL | ALLOW_ALL
  message: "&cWhitelist unavailable. Try again later."

logging:
  denied: true
  denied-to-file: true
  file-name: "denied.log"

webhook:
  enabled: false
  url: ""
  on-denied: true
  on-ratelimit: true
  on-failsafe: true
  timeout-ms: 3000
  format: "JSON"
```

### Key options

* `enabled` - enables or disables IP filtering globally
* `messages` - fully customizable plugin messages (supports color codes)
* `proxy.mode` - switch between direct and proxy modes (DIRECT/BUNGEE/VELOCITY)
* `proxy.trusted-forwarded-ips` - list of trusted proxy IPs used as a gate
* `ratelimit` - connection attempt throttling
* `failsafe` - what to do when storage/proxy checks fail
* `logging` - audit logging for denied connections
* `webhook` - optional JSON notifications for denies

### Whitelist entry formats

Whitelist entries accept only IPv4 values in these formats:

* Exact IP: `203.0.113.10`
* CIDR block: `203.0.113.0/24`
* Range: `203.0.113.10-203.0.113.50`

Entries are normalized when saved to `ips.yml`.

---

## 🧾 Commands

| Command            | Description                     |
| ------------------ | ------------------------------- |
| `/ipf add <ip>`    | Add an IP to the whitelist      |
| `/ipf remove <ip>` | Remove an IP from the whitelist |
| `/ipf list`        | Show all whitelisted IPs        |
| `/ipf status`      | Show plugin diagnostics         |
| `/ipf reload`      | Reload config and whitelist     |
| `/ipf on`          | Enable IP filtering             |
| `/ipf off`         | Disable IP filtering            |

---

## 🔐 Permissions

| Permission              | Description           | Default |
| ----------------------- | --------------------- | ------- |
| `betteripfilter.admin`  | Full access           | OP      |
| `betteripfilter.add`    | Add IPs               | OP      |
| `betteripfilter.remove` | Remove IPs            | OP      |
| `betteripfilter.list`   | View whitelist        | OP      |
| `betteripfilter.status` | View status           | OP      |
| `betteripfilter.reload` | Reload plugin data    | OP      |
| `betteripfilter.toggle` | Enable/disable filter | OP      |

> The plugin uses standard Bukkit permissions and works seamlessly with **LuckPerms**.

---

## 🧠 How It Works

* The plugin listens to `AsyncPlayerPreLoginEvent`
* The player’s IP address is checked **before** they fully join the server
* Whitelisted IPs are stored in memory (`HashSet`) for O(1) lookup
* If the IP is not allowed, the connection is denied immediately
* No permission bypass is used by design to keep checks fast and secure
* Proxy mode does not parse forwarded headers. It relies on Paper/Proxy IP forwarding being configured, and
  uses `proxy.trusted-forwarded-ips` as a gate for incoming proxy addresses.
* Rate limiting throttles rapid login attempts based on source IP
* Failsafe mode controls what happens when storage or proxy trust is unavailable
* Denied log entries include IP addresses (privacy note: treat logs as sensitive)

---

## 🚀 Performance Notes

* No scheduled tasks
* No reflection or NMS usage
* Constant-time IP lookups
* Thread-safe handling for async login events
* Negligible memory footprint

Designed to run on production servers with zero noticeable overhead.

---

## 📁 Plugin File Structure

After first launch:

```
plugins/Better-IP-Filter/
├── Better-IP-Filter.jar
├── config.yml
└── ips.yml
```

---

## 🛠 Build from Source

```bash
mvn clean package
```

The compiled JAR will be available in:

```
target/Better-IP-Filter-1.0.0.jar
```

---

## ✅ Compatibility

* ✔ Paper/Spigot/etc
* ✔ Minecraft 1.21 – 1.21.11

---

## 📄 License

This project is licensed under the **MIT License**.
You are free to use, modify, and distribute it in both private and commercial projects.

---

**Better-IP-Filter** — simple, fast, and secure IP filtering for modern Paper servers.
