# Cabalero's Project Metal (Android)

App Android nativo (Jetpack Compose) para consulta de furos recomendados para roscas:

- Sistema metrico (MM)
- Sistema em polegada (ISO 228-1, BSPP)

## Como abrir

1. Abra a pasta `apps/cabalero-project-metal-android` no Android Studio.
2. Aguarde a sincronizacao Gradle.
3. Rode no emulador/dispositivo Android.

## Base tecnica ISO

- Rosca metrica: ISO 261 + ISO 724
- Rosca em polegada: ISO 228-1
- Perfil de rosca em polegada: ISO 68-2

## Observacao

O calculo implementado para consulta rapida usa:

`furo recomendado ~= diametro maior - passo`

Para usinagem critica/tolerancias especificas, valide com a tabela oficial da empresa.

