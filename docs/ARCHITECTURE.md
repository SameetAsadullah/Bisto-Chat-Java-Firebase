# Architecture

Bisto is an Android client backed by Firebase services.

## Application layers

- **Activities and fragments** implement authentication, profile setup, contact lists, chat lists, conversations, groups, and call screens.
- **RecyclerView adapters** render contacts, conversations, calls, group members, text messages, and image messages.
- **Model classes** represent accounts, contacts, recent conversations, calls, and messages.
- **Firebase Authentication** manages account registration and sign-in.
- **Firebase Realtime Database** synchronizes profiles and conversation data.
- **Firebase Storage** stores profile and message images.
- **Local SQLite** supports device-side application state through `DBHelper`.

## Main user flow

```text
Splash → Sign in / Sign up → Profile setup → Contacts → Conversation
```

The client also observes screenshots during a conversation and can publish the captured image as a chat event, subject to Android storage permissions.

## Historical scope

This is an academic Android project from 2021 and retains its original dependency versions and data model. Before production use, the data model should avoid duplicating authentication passwords, Firebase Security Rules should enforce per-user access, and current Android privacy/storage APIs should replace the legacy permission flow.
