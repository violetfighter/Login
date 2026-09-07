import SwiftUI
import FirebaseCore

@main
//Starting point for IOS
struct iOSApp: App {
    init() {
        FirebaseApp.configure()// wakes up Firebase for iOS
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
