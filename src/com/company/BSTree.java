package com.company;

import sun.reflect.generics.tree.Tree;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Stack;
import java.util.function.Consumer;

public class BSTree<T1 extends Comparable> implements java.lang.Iterable<T1> {

    private Node<T1> Root;

    @Override
    public Iterator<T1> iterator() {

        return new iter();

    }

    public BSTree() {
        Root = null;
    }

    public void Add(T1 value) {
        if (Root == null) {
            Root = new Node<T1>(value);
            return;
        }
        add(Root, value);
    }

    private void add(Node<T1> root, T1 value) {
        if (root.Value.equals(value)) {
            return;
        }

        if (value.compareTo(root.Value) > 0) {
            if (root.Right == null) {
                root.Right = new Node<>(value);
                root.Right.Parent = root;
                return;
            }
            add(root.Left, value);
        } else {
            if (root.Left == null) {
                root.Left = new Node<>(value);
                root.Left.Parent = root;
            }

        }
        add(root.Left, value);
    }

    public boolean Find(T1 value) {
        return find(Root, value);
    }

    private boolean find(Node<T1> node, T1 value){
        if(node == null){
            return false;
        }
        if(node.Value.equals(value)){
            return true;
        }
        return find(value.compareTo(node.Value)>0 ? node.Right: node.Left, value);
    }
    public void Remove(T1 value){
        remove(Root, value);

    }

    private void remove(Node<T1> root, T1 value) {
        if(root == null){
            return;
        }
        if(root.Value.equals(value)){
            refNode(root);
            return;
        }
        remove(value.compareTo(root.Value)>0? root.Right: root.Left, value);
    }

    private void refNode(Node<T1> node){
        Node<T1> refresh = null;

        if(node.Right != null && node.Left != null){
            refresh = node.Left;
            Node<T1> tmp = refresh;
            while (tmp.Right != null){
                tmp = tmp.Right;
            }
            tmp.Right = node.Right;
            node.Right.Parent = tmp;

        } else {
            refresh = node.Left != null ? node.Left : node.Right;
        }

        if(node.Parent == null) {
            Root = refresh;
        }else {
            if(node.equals(node.Parent.Left)){
                node.Parent.Left = refresh;
            }else {
                node.Parent.Right = refresh;
            }
        }
        if(refresh != null){
            refresh.Parent = node.Parent;
        }

    }

    public boolean isEmpty() {
        return Root == null;
    }

    class iter implements Iterator<T1>{
        private Node<T1> current;
        private Node<T1> next;

        public iter(){
            current = null;
            next = Root;
            while (next != null && next.Left != null){
                next = next.Left;
            }
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }




        @Override
        public T1 next() {
            if(next == null){
                throw new NoSuchElementException();
            }
            current = next;
            next = getNext();
            return current.Value;
        }
        public void remove() {
            if (current == null) {
                throw new IllegalStateException();
            }

            refNode(current);
        }
        private Node<T1> getNext() {
            if (next.Right != null) {
                Node<T1> temp = next.Right;

                while (temp.Left != null) {
                    temp = temp.Left;
                }

                return temp;
            }

            Node<T1> temp = next;
            while (temp.Parent != null && temp.equals(temp.Parent.Right)) {
                temp = temp.Parent;
            }

            return temp.Parent;
        }

    }
}
