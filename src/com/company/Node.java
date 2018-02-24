package com.company;

public class Node<T> {
    public T Value;
    public Node<T> Left, Right, Parent;

    public Node(T value){
        Value = value;
        Left = null;
        Right = null;
        Parent = null;
    }
}
