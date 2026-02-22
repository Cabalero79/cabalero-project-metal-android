# Publicacao Google Play (2026)

## 1) Requisitos tecnicos minimos

- `targetSdk` em `35` (Android 15) para novos updates em 2026.
- App assinado com keystore de release.
- Bundle `AAB` de release.
- Politicas de privacidade e Data Safety preenchidas.

Referencias oficiais:
- https://developer.android.com/google/play/requirements/target-sdk
- https://developer.android.com/studio/publish/app-signing
- https://support.google.com/googleplay/android-developer/answer/9859152

## 2) Assinatura de release

Defina variaveis de ambiente antes do build:

```bash
set ANDROID_KEYSTORE_PATH=C:\keys\cabalero-release.jks
set ANDROID_KEYSTORE_PASSWORD=***
set ANDROID_KEY_ALIAS=***
set ANDROID_KEY_PASSWORD=***
```

## 3) Build do Android App Bundle

```bash
cd apps/cabalero-project-metal-android
./gradlew clean bundleRelease
```

Saida esperada:

- `app/build/outputs/bundle/release/app-release.aab`

## 4) Publicar na Play Console

1. Criar app na Play Console.
2. Ativar Play App Signing.
3. Enviar `app-release.aab`.
4. Completar:
- Data Safety
- Content Rating
- App Access (se aplicavel)
- Politicas de privacidade
5. Criar release em `Internal testing` e validar.
6. Promover para `Production`.

## 5) Checklist de seguranca/conformidade aplicado neste projeto

- `usesCleartextTraffic=false`
- `networkSecurityConfig` com cleartext bloqueado
- `allowBackup=false`
- build release com `minifyEnabled=true`
- build release com `shrinkResources=true`
- assinatura de release via variaveis de ambiente

## 6) Checklist manual obrigatorio (nao automatizavel sem sua conta)

- Revisao de politicas Google Play
- Preenchimento de formularios da Play Console
- Declaracoes de dados e permissoes
- Publicacao e rollout no canal de producao
