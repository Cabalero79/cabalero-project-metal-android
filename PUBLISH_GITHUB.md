# Publicar no GitHub

## Observacao importante

Neste monorepo, `apps/` esta no `.gitignore`. O caminho recomendado e publicar este app em um repositorio proprio.

## Passo a passo (repositorio novo)

```bash
cd apps/cabalero-project-metal-android
git init
git add .
git commit -m "feat: cabalero project metal android app"
git branch -M main
git remote add origin https://github.com/SEU_USUARIO/cabalero-project-metal-android.git
git push -u origin main
```

## Opcional (GitHub CLI)

```bash
cd apps/cabalero-project-metal-android
gh repo create cabalero-project-metal-android --public --source=. --remote=origin --push
```
