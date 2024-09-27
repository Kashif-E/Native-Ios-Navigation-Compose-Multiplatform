# Compose Multiplatform Application with IOS native Navigation for IOS App in common code and Voyager for other platforms
- Looking to build with KMP and CMP? Let's connect [LinkedIn](https://www.linkedin.com/in/kashif-mehmood-km/)
- Featured in [Kotlin Weekly](https://mailchi.mp/kotlinweekly/kotlin-weekly-422)

#### Want to support my work ?
<a href="https://www.buymeacoffee.com/kashifmehmood"><img src="https://img.buymeacoffee.com/button-api/?text=Buy me a coffee&emoji=&slug=kashifmehmood&button_colour=FFDD00&font_colour=000000&font_family=Cookie&outline_colour=000000&coffee_colour=ffffff" /></a>



## Experiment: iOS Navigation with Compose Multiplatform 🔴🚧⌛🔄🟢

This project is an experiment to explore how iOS navigation can be implemented using Compose Multiplatform. The goal is to leverage Kotlin Multiplatform capabilities to create a seamless navigation experience on both Android and iOS platforms.

### How It Works

1. **Navigation Setup**: 
   - The `Navigator` class is used to manage navigation between different screens.
   - The `BottomSheetNavigator` is used to handle bottom sheet navigation.

2. **View Controller Wrapping**:
   - The `extendedComposeViewController` function creates a `UIViewController` that hosts a Compose UI.
   - The `UIViewControllerWrapper` class wraps another `UIViewController` and adds gesture recognizer functionality to handle swipe gestures.

3. **Gesture Handling**:
   - The `UIViewControllerWrapper` implements the `UIGestureRecognizerDelegateProtocol` to handle swipe gestures.
   - Swipe gestures are added to the view controller to enable navigation through swiping.

4. **Interactive Pop Gesture**:
   - The `interactivePopGestureRecognizer` is enabled for the entire screen to allow swipe-back navigation.
   - The delegate for the `interactivePopGestureRecognizer` is set to the `UIViewControllerWrapper` to manage the gesture.

## Demo 
[![Video](https://img.youtube.com/vi/HdPoG59DYws/0.jpg)](https://www.youtube.com/shorts/HdPoG59DYws)

