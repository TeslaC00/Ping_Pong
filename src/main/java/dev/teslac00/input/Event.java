package dev.teslac00.input;

//TODO: use scancode for layout independence
//TODO: expand event to represent mouseEvent
//TODO: show release and press event as separate
//TODO: handle window events or other
public record Event(int key, int action) {
}
