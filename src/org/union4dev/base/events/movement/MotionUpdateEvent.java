package org.union4dev.base.events.movement;

import org.union4dev.base.events.base.Event;

public class MotionUpdateEvent extends Event.EventCancellable {

    private float yaw, pitch;
    private double x, y, z;
    private boolean onGround;
    private boolean rotate;
    private final boolean isPre;

    public MotionUpdateEvent(float yaw, float pitch, double x, double y, double z, boolean onGround) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.x = x;
        this.y = y;
        this.z = z;
        this.onGround = onGround;
        this.rotate = false;
        this.isPre = true;
    }

    public MotionUpdateEvent() {
        this.isPre = false;
    }

    public boolean isPre() {
        return isPre;
    }

    public boolean isPost() {
        return !isPre;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
        this.rotate = true;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public boolean isRotate() {
        return rotate;
    }
}
