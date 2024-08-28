package com.kashif.sample.voyager

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import platform.Foundation.NSLog
import platform.Foundation.NSSelectorFromString
import platform.UIKit.UIGestureRecognizer
import platform.UIKit.UIGestureRecognizerDelegateProtocol
import platform.UIKit.UISwipeGestureRecognizer
import platform.UIKit.UISwipeGestureRecognizerDirectionLeft
import platform.UIKit.UISwipeGestureRecognizerDirectionRight
import platform.UIKit.UIViewController
import platform.UIKit.addChildViewController
import platform.UIKit.didMoveToParentViewController
import platform.UIKit.navigationController
import platform.UIKit.willMoveToParentViewController

/**
 * A custom `UIViewController` that wraps another `UIViewController` and adds gesture recognizer functionality.
 * Implements the `UIGestureRecognizerDelegateProtocol` to handle swipe gestures.
 *
 * @property controller The `UIViewController` instance that is being wrapped.
 */
class UIViewControllerWrapper(
    private val controller: UIViewController,
) : UIViewController(null, null), UIGestureRecognizerDelegateProtocol {

    /**
     * Called when the view controller's view is loaded into memory.
     * Sets up the view hierarchy by adding the wrapped controller's view as a subview
     * and managing the parent-child relationship between the view controllers.
     */
    @OptIn(ExperimentalForeignApi::class)
    override fun loadView() {
        super.loadView()
        controller.willMoveToParentViewController(this)
        controller.view.setFrame(view.frame)
        view.addSubview(controller.view)
        addChildViewController(controller)
        controller.didMoveToParentViewController(this)
    }

    /**
     * Called after the view has been loaded.
     * Sets the delegate for the interactive pop gesture recognizer and adds swipe gesture recognizers
     * for left and right swipe directions.
     */
    @OptIn(ExperimentalForeignApi::class)
    override fun viewDidLoad() {
        super.viewDidLoad()
        controller.navigationController?.interactivePopGestureRecognizer?.delegate = this

        val directions =
            listOf(UISwipeGestureRecognizerDirectionRight, UISwipeGestureRecognizerDirectionLeft)
        for (direction in directions) {
            val gesture = UISwipeGestureRecognizer(
                target = this,
                action = NSSelectorFromString("handleSwipe:")
            )
            gesture.direction = direction
            gesture.delegate = this
            this.view.addGestureRecognizer(gesture)
        }
    }

    /**
     * Handles the swipe gestures detected by the gesture recognizers.
     * Logs the direction of the swipe.
     *
     * @param sender The `UISwipeGestureRecognizer` that detected the swipe.
     */
    @OptIn(BetaInteropApi::class)
    @ObjCAction
    fun handleSwipe(sender: UISwipeGestureRecognizer) {
        NSLog("Swipe detected: ${sender.direction}")
    }

    /**
     * Determines whether the gesture recognizer should begin interpreting touches.
     * Always returns `true`.
     *
     * @param gestureRecognizer The `UIGestureRecognizer` that is asking whether it should begin.
     * @return `true` to allow the gesture recognizer to begin.
     */
    override fun gestureRecognizerShouldBegin(gestureRecognizer: UIGestureRecognizer): Boolean {
        return true
    }

    /**
     * Determines whether the gesture recognizer should be required to fail by another gesture recognizer.
     * Always returns `true`.
     *
     * @param gestureRecognizer The `UIGestureRecognizer` that is asking whether it should be required to fail.
     * @param shouldBeRequiredToFailByGestureRecognizer The `UIGestureRecognizer` that is requiring the failure.
     * @return `true` to require the gesture recognizer to fail.
     */
    override fun gestureRecognizer(
        gestureRecognizer: UIGestureRecognizer,
        shouldBeRequiredToFailByGestureRecognizer: UIGestureRecognizer
    ): Boolean {
        return true
    }
}