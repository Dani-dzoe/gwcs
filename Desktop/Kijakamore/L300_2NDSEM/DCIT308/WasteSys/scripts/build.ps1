$out = Join-Path $PSScriptRoot '..\out'
if (Test-Path $out) { Remove-Item $out -Recurse -Force }
New-Item -ItemType Directory -Path $out | Out-Null
$sources = Get-ChildItem -Path (Join-Path $PSScriptRoot '..\src\main\java') -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -d $out $sources
if ($LASTEXITCODE -ne 0) { throw "javac failed with exit code $LASTEXITCODE" }
Write-Host "Compiled to $out"
