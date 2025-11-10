Claro, para tenerlo listo para descargar, te dejo el contenido completo del `README.md` que puedes copiar y pegar en un archivo en tu PC:

1. Abre un editor de texto (VS Code, Notepad++, Sublime, incluso Bloc de notas).
2. Crea un nuevo archivo y pégale esto:

````markdown
# 📍 GeoTracker App

App Android en Kotlin para rastrear la ubicación del dispositivo y enviarla a un servidor simple (Node.js).

---

## 🚀 Funcionalidades

- ▶️ Iniciar rastreo de ubicación
- ⏹ Detener rastreo
- 📤 Enviar ubicación simulada (para pruebas)
- Guardar ubicaciones en JSON
- Enviar ubicaciones a un servidor local

---

## 🛠 Requisitos

- Android Studio 2022+  
- Android SDK 26+  
- Node.js (para el servidor)  
- PC y celular en la **misma red** para pruebas

---

## 📦 Instalación

### 1️⃣ App Android

1. Clonar repo:
   ```bash
   git clone <URL_DEL_REPOSITORIO>
````

2. Abrir `GeoTrackerApp/` en Android Studio
3. Construir y ejecutar en tu dispositivo
4. Conceder permisos de ubicación y foreground service

### 2️⃣ Servidor Node.js

1. Entrar a la carpeta `server/`
2. Instalar dependencias:

   ```bash
   npm install
   ```
3. Iniciar servidor:

   ```bash
   node server.js
   ```
4. Acceder desde la app usando `http://<IP_PC>:3000/locations`

---

## ⚡ Uso

* Pulsa **Iniciar rastreo** para obtener la ubicación real
* Pulsa **Enviar ubicación simulada** para probar sin GPS
* Pulsa **Detener rastreo** para finalizar
* La app guardará localmente en JSON y enviará datos al servidor si está activo

---

## 📂 Estructura del proyecto

```
GeoTracker/
├─ GeoTrackerApp/      # Proyecto Android Studio
├─ server/             # Servidor Node.js simple
└─ README.md
```
