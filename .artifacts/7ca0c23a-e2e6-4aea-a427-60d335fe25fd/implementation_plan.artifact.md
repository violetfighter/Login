# Implementation Plan - Fixing "Forgot Password" Email Flow

The user is unable to send a "Forgot Password" email. The current implementation uses `sendSignInLinkToEmail` (intended for passwordless login) instead of the standard `sendPasswordResetEmail`. Additionally, there is a mismatch between the UI, ViewModel, and Repository methods.

## User Review Required

> [!IMPORTANT]
> I will switch the functionality from "Magic Link" (passwordless sign-in) to a standard "Password Reset" email. This is much simpler and directly addresses the "Forgot Password" use case.
>
> You must ensure that **Email/Password** authentication is enabled in your Firebase Console (Authentication > Sign-in method).

## Proposed Changes

### [Component: Data Layer]

#### [MODIFY] [UserRepository.kt](file:///Users/parvathim/AndroidStudioProjects/Login 2/shared/src/commonMain/kotlin/com/cfcici/in/project/data/repository/UserRepository.kt)
- Rename `sendLoginLink` to `sendPasswordResetEmail`.
- Update the implementation to call `Firebase.auth.sendPasswordResetEmail(email)`.
- Keep `ActionCodeSettings` optional or simplified for basic reset emails.

### [Component: ViewModel Layer]

#### [MODIFY] [UserViewModel.kt](file:///Users/parvathim/AndroidStudioProjects/Login 2/shared/src/commonMain/kotlin/com/cfcici/in/project/viewmodel/UserViewModel.kt)
- Rename `sendLoginLink` to `sendPasswordReset`.
- Ensure it calls the new repository method and returns the result through the `onResult` callback.
- Add logging (`e.printStackTrace()`) to catch specific Firebase errors.

### [Component: UI Layer]

#### [MODIFY] [SendEmailPage.kt](file:///Users/parvathim/AndroidStudioProjects/Login 2/shared/src/commonMain/kotlin/com/cfcici/in/project/ui/SendEmailPage.kt)
- Update the button `onClick` to call `viewModel.sendPasswordReset(emailText)`.
- Improve error handling and status feedback for the user.

## Verification Plan

### Automated Tests
- **Build**: Run `./gradlew :shared:assemble` to verify compilation.
- **Sync**: Verify Gradle sync completes successfully.

### Manual Verification
- **Test with a real email**: Enter a valid email address in the app and check for the "Email sent successfully!" message.
- **Check Inbox**: Verify that a password reset email arrives from Firebase.
- **Logcat Analysis**: If it still fails, check Logcat for specific error codes (e.g., `ERROR_INVALID_EMAIL`, `ERROR_USER_NOT_FOUND`).
