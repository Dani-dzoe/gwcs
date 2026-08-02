$root = Split-Path $PSScriptRoot -Parent
& (Join-Path $PSScriptRoot 'build.ps1')
java -ea -cp (Join-Path $root 'out') gh.edu.ug.wastesys.cli.SelfTestRunner
