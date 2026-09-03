# Jenkins CI/CD setup

This document covers the one-time setup that has to happen in the Jenkins UI and
Google Play Console — everything the `Jenkinsfile` itself can't express.

## 1. Job type

Create a **Multibranch Pipeline** job pointed at this GitHub repo:

- Branch discovery: all branches (this is what makes "push to any branch runs
  tests + build" work — a single-branch Pipeline job can't do this).
- Build trigger: GitHub webhook (push events).
- Script path: `Jenkinsfile` (repo root, default).

`staging` currently only exists as a local branch — it hasn't been pushed to
`origin`. Push it before relying on the staging pipeline:

```sh
git push -u origin staging
```

## 2. Required Jenkins credentials

Create these in the job/folder's credentials store with **exactly** these IDs —
the `Jenkinsfile` references them by ID:

| Credential ID | Type | Contents |
|---|---|---|
| `android-release-keystore` | Secret file | The `.jks` keystore file (see §3) |
| `android-keystore-password` | Secret text | Keystore password |
| `android-key-alias` | Secret text | Key alias |
| `android-key-password` | Secret text | Key password |
| `play-service-account-json` | Secret file | Google Play service-account JSON key |

## 3. Generating the release keystore

No release keystore exists yet. Generate it yourself (not through Jenkins or an
assistant) since it's the app's permanent signing identity — if it's lost, this
applicationId (`by.freiding.braindrop`) can never be updated again on Play under
that identity; if it leaks, whoever has it can publish updates in your name.

```sh
keytool -genkeypair -v \
  -keystore braindrop-release.jks \
  -alias braindrop \
  -keyalg RSA -keysize 2048 -validity 10000
```

Then:
1. Store the resulting `.jks` file and the passwords you chose in a password
   manager / secrets vault.
2. Upload the `.jks` file and the three passwords/alias as the four credentials
   listed in §2.
3. Delete any local copies outside your password manager.

## 4. Closed testing track name

The `Jenkinsfile`'s "Publish Closed Testing" stage uses the Gradle property
`-PplayTrack=closed-testing` as a placeholder. Google Play lets you name closed
testing tracks freely, so replace `closed-testing` in the `Jenkinsfile` with the
exact track name/alias configured in **Play Console → Testing → Closed testing
→ your track → Track name**.

The internal testing track doesn't need this — `internal` is a fixed track name
in the Play Developer API.

## 5. versionCode

`app/build.gradle.kts` sets `play.resolutionStrategy = ResolutionStrategy.AUTO`,
so versionCode is **not** driven by Jenkins' `BUILD_NUMBER`. That was the first
approach considered, but a Multibranch Pipeline gives every branch its own
independent `BUILD_NUMBER` sequence — `staging` build #5 and `main` build #5
would collide, and Play requires versionCode to be unique and strictly
increasing across the whole app, not per track.

With `AUTO`, Gradle Play Publisher queries Play Console for the current highest
versionCode (across all tracks) at publish time and uses `max + 1`, regardless
of which branch or job produced the build. `defaultConfig.versionCode` in
`app/build.gradle.kts` stays a static placeholder — it's only used for local/
debug builds that never reach Play.

## 6. What each branch does

| Trigger | Lint + Tests | Debug build | Signed release bundle | Publish |
|---|---|---|---|---|
| any branch push | ✅ | ✅ | – | – |
| `staging` push | ✅ | ✅ | ✅ | Google Play Internal testing |
| `main` push | ✅ | ✅ | ✅ | Google Play Closed testing |

Test and lint results are published back to GitHub as commit status checks by
the Multibranch Pipeline job (via the GitHub Branch Source plugin) — no extra
Jenkinsfile config needed for that, as long as the job is the Multibranch type
described in §1.
