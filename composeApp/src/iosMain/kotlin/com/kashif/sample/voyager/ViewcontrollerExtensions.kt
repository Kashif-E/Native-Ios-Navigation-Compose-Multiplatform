package com.kashif.sample.voyager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.uikit.OnFocusBehavior
import androidx.compose.ui.window.ComposeUIViewController
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import com.kashif.sample.theme.AppTheme
import platform.Foundation.NSLog
import platform.UIKit.UIApplication
import platform.UIKit.UINavigationController
import platform.UIKit.UITabBarController
import platform.UIKit.UIViewController
import platform.UIKit.childViewControllers

/**
 * Retrieves the top `UIViewController` from the view hierarchy.
 *
 * @param base The base `UIViewController` to start the search from. Defaults to the root view controller of the key window.
 * @return The top `UIViewController`, or null if none is found.
 */
fun getTopViewController(base: UIViewController? = UIApplication.sharedApplication().keyWindow?.rootViewController): UIViewController? {
    when {
        base is UINavigationController -> {
            return getTopViewController(base = base.visibleViewController)
        }
        base is UITabBarController -> {
            return getTopViewController(base = base.selectedViewController)
        }
        base?.presentedViewController != null -> {
            return getTopViewController(base = base.presentedViewController)
        }
        base.toString().contains("HostingController") -> return getTopViewController(
            base = base?.childViewControllers()?.first() as UIViewController
        )
        else -> {
            return base
        }
    }
}

/**
 * Logs the hierarchy of the top `UIViewController` for debugging purposes.
 *
 * @param base The base `UIViewController` to start the search from. Defaults to the root view controller of the key window.
 */
fun debugTopViewController(base: UIViewController? = UIApplication.sharedApplication().keyWindow?.rootViewController) {
    if (base is UINavigationController) {
        NSLog("TopViewController: UINavigationController with visible view controller: ${base.visibleViewController}")
        debugTopViewController(base = base.visibleViewController)
    } else if (base is UITabBarController) {
        NSLog("TopViewController: UITabBarController with selected view controller: ${base.selectedViewController}")
        debugTopViewController(base = base.selectedViewController)
    } else if (base?.presentedViewController != null) {
        NSLog("TopViewController: Presented view controller: ${base.presentedViewController}")
        debugTopViewController(base = base.presentedViewController)
    } else {
        NSLog("TopViewController: ${base}")
    }
}

/**
 * Creates a `UIViewController` that hosts a Compose UI.
 *
 * @param modifier The `Modifier` to be applied to the Compose UI.
 * @param screen The `Screen` to be displayed in the Compose UI.
 * @param isOpaque Whether the view controller's view is opaque.
 * @return A `UIViewController` that hosts the Compose UI.
 */
@OptIn(ExperimentalComposeApi::class, ExperimentalMaterialApi::class)
fun extendedComposeViewController(
    modifier: Modifier = Modifier,
    screen: Screen,
    isOpaque: Boolean = true,
): UIViewController {
    val uiViewController = ComposeUIViewController(configure = {
        onFocusBehavior = OnFocusBehavior.DoNothing
        opaque = isOpaque
    }) {
        AppTheme {
            Box(
                modifier = modifier.imePadding()
                    .padding(top = WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding())
            ) {
                BottomSheetNavigator {
                    Navigator(screen = screen)
                }
            }
        }
    }

    return UIViewControllerWrapper(uiViewController)
}