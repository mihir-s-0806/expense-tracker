# 🚀 Smart Personal Expense & Budget Tracker - Render.com Deployment Guide

This repository is fully configured for **100% Free Deployment** on **[Render.com](https://render.com)**.

---

## 📌 Step-by-Step Deployment to Render.com

### Step 1: Push Code to GitHub

Open a terminal or PowerShell inside `C:\Users\sevak\.gemini\antigravity\scratch\expense-tracker` and execute:

```bash
# 1. Initialize Git repository
git init

# 2. Add all files and commit
git add .
git commit -m "Configure Spring Boot Expense Tracker for Render deployment"

# 3. Create a new repository on GitHub (https://github.com/new) named "expense-tracker"

# 4. Link your remote repository and push (Replace with your actual GitHub username)
git branch -M main
git remote add origin https://github.com/YOUR_GITHUB_USERNAME/expense-tracker.git
git push -u origin main
```

---

### Step 2: Deploy on Render.com (Free)

1. Sign up / Log in to **[Render.com](https://render.com)** (you can log in directly with your GitHub account).
2. Click **New +** at the top right and select **Web Service**.
3. Choose **Build and deploy from a Git repository** and connect your `expense-tracker` GitHub repository.
4. Fill in the deployment details:
   - **Name:** `expense-tracker` *(or any custom name)*
   - **Region:** Choose the nearest region (e.g. Singapore / Frankfurt / Oregon)
   - **Environment / Runtime:** Select **Docker**
   - **Instance Type:** Select **Free** ($0/month)
5. Click **Create Web Service**.

---

### 🎉 Step 3: Access Your Live Global Web App!

Render will build your Docker container and start your Spring Boot server automatically.

Once the deployment finishes (takes ~2 minutes), Render will display your live global HTTPS URL at the top of the dashboard:

```
https://expense-tracker-xxxx.onrender.com
```

You can now open this link on your smartphone, laptop, or share it with anyone in the world!

---

## 🧪 Local Testing & Verification

To run locally before pushing:
```bash
mvn spring-boot:run
```
Open [http://localhost:8080](http://localhost:8080) in your browser.
