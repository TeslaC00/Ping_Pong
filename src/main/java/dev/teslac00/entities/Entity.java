/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : Entity
 * Author  : Vikas Kumar
 * Created : 19-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.entities;

public abstract class Entity {

    public abstract void update(double deltaTime);

    public abstract void destroy();
}
