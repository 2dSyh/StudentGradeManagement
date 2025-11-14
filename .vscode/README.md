### VS Code settings for this project

Purpose: explain the `.vscode/settings.json` entries and why we prefer UTF-8 (no BOM).

- `files.encoding: "utf8"` — ensures editors save files as UTF-8 without BOM. This avoids Java compiler errors like `unmappable character` or `illegal character` caused by BOM.
- `files.eol: "\n"` — use LF line endings for consistency across platforms.

If you need to change editor behavior locally, update your user settings. Project rules are enforced by `.editorconfig` and `.gitattributes`.

Quick checks:

1. Detect BOM (PowerShell):
```
$bytes = [System.IO.File]::ReadAllBytes('src\path\to\File.java'); $bytes[0..2] -join ','
```

2. Remove BOM (PowerShell):
```
$p = 'src\path\to\File.java'
$c = [System.IO.File]::ReadAllText($p)
[System.IO.File]::WriteAllText($p, $c, New-Object System.Text.UTF8Encoding($false))
```

Contact: see PR description for context. If you prefer I can add a CI step to reject BOM files automatically.
