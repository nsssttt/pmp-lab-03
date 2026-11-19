$ErrorActionPreference = 'Stop'
$javaCandidates = @(
    "$env:JAVA_HOME/bin/java.exe",
    "$env:ProgramFiles/Android/Android Studio/jbr/bin/java.exe"
)
$javaExe = $javaCandidates | Where-Object { Test-Path -LiteralPath $_ } | Select-Object -First 1
if (-not $javaExe) {
    $javaExe = (Get-Command java.exe -ErrorAction Stop).Source
}
if (-not $env:ANDROID_HOME) {
    $sdkCandidate = Join-Path $env:LOCALAPPDATA 'Android/Sdk'
    if (Test-Path -LiteralPath $sdkCandidate) { $env:ANDROID_HOME = $sdkCandidate }
}
Push-Location -LiteralPath $PSScriptRoot
try {
    # A relative JAR path supports Ukrainian folder names on Windows.
    if ($args.Count -eq 0) { $buildArgs = @('assembleDebug', 'lintDebug') }
    else { $buildArgs = $args }
    & $javaExe '-Dfile.encoding=UTF-8' -jar 'gradle/wrapper/gradle-wrapper.jar' @buildArgs '--console' 'plain'
    $buildExitCode = $LASTEXITCODE
} finally { Pop-Location }
exit $buildExitCode
