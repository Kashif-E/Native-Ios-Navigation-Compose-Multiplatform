import androidx.compose.ui.window.ComposeUIViewController
import com.kashif.sample.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
