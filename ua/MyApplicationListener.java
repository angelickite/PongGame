package ua;

import com.badlogic.gdx.ApplicationListener;

/**
 * <p>
 * An <code>ApplicationListener</code> is called when the {@link Application} is
 * created, resumed, rendering, paused or destroyed. All methods are called in a
 * thread that has the OpenGL context current. You can thus safely create and
 * manipulate graphics resources.
 * </p>
 * 
 * <p>
 * The <code>ApplicationListener</code> interface follows the standard Android
 * activity life-cycle and is emulated on the desktop accordingly.
 * </p>
 * 
 * @author mzechner
 */
public interface MyApplicationListener extends ApplicationListener {
	public void onRenderException(Exception e);
}
