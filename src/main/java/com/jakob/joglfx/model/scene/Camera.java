package com.jakob.joglfx.model.scene;

import com.jakob.joglfx.model.animation.CameraAnimator;
import javafx.beans.property.SimpleObjectProperty;
import org.joml.Vector3f;

public class Camera {

    SimpleObjectProperty<Vector3f> eye;
    SimpleObjectProperty<Vector3f> center;
    SimpleObjectProperty<Vector3f> up;

    CameraAnimator animator;

    public Camera(Vector3f eye, Vector3f center, Vector3f up) {
        this.eye = new SimpleObjectProperty<>(eye);
        this.center = new SimpleObjectProperty<>(center);
        this.up = new SimpleObjectProperty<>(up);
    }

    public Camera() {
        this.eye = new SimpleObjectProperty<>(new Vector3f(5,5,5));
        this.center = new SimpleObjectProperty<>(new Vector3f(0,0,0));
        this.up = new SimpleObjectProperty<>(new Vector3f(0,1,0));
    }


    public Vector3f getEye() {
        return eye.get();
    }

    public SimpleObjectProperty<Vector3f> eyeProperty() {
        return eye;
    }

    public void setEye(Vector3f eye) {
        this.eye.set(eye);
    }

    public Vector3f getCenter() {
        return center.get();
    }

    public SimpleObjectProperty<Vector3f> centerProperty() {
        return center;
    }

    public void setCenter(Vector3f center) {
        this.center.set(center);
    }

    public Vector3f getUp() {
        return up.get();
    }

    public SimpleObjectProperty<Vector3f> upProperty() {
        return up;
    }

    public void setUp(Vector3f up) {
        this.up.set(up);
    }
}
