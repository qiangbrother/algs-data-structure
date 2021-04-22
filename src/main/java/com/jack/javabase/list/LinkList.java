package com.jack.javabase.list;

import java.util.NoSuchElementException;

/**
 * @Description: 简单的实现LinkedList
 * @Author: JackQ
 * @CreateDate: 2019/12/19 17:31
 */
public class LinkList<E> {


    int size;

    LinkList.Node first;

    LinkList.Node last;

    public LinkList() {
        size = 0;
    }

    public int size() {
        return this.size;
    }

    public boolean add(E var1) {
        this.linkLast(var1);
        return true;
    }

    public E get(int i) {
        boolean checkFlag = this.checkIndex(i);
        if (checkFlag) {
            return this.node(i).item;
        } else {
            throw new NoSuchElementException();
        }
    }

    public boolean remove(int i) {
        if (checkIndex(i)) {
            this.removeLink(this.node(i));
            return true;
        } else {
            throw new NoSuchElementException();
        }
    }

    void removeLink(LinkList.Node var1) {
        Object var2 = var1.item;
        LinkList.Node var3 = var1.prev;
        LinkList.Node var4 = var1.next;
        if (var4 == null) {
            this.last = var3;
        } else {
            var3.next = var4;
            var1.next = null;
        }

        if (var3 == null) {
            this.first = var4;
        } else {
            var4.prev = var3;
            var1.prev = null;
        }

        --this.size;
    }

    private boolean checkIndex(int i) {
        if (i <= this.size && this.size >= 1) {
            return true;
        }
        return false;
    }

    public E getFirst() throws Exception {
        LinkList.Node node = this.first;
        if (node == null) {
            throw new Exception();
        }
        return (E) node.item;

    }

    public E getLast() throws Exception {
        LinkList.Node node = this.last;
        if (node == null) {
            throw new Exception();
        }
        return (E) node.item;
    }

    void linkLast(E var1) {
        LinkList.Node var2 = this.last;
        LinkList.Node var3 = new LinkList.Node(var2, var1, null);
        this.last = var3;
        if (var2 == null) {
            this.first = var3;
        } else {
            var2.next = var3;
        }

        ++this.size;
    }

    private LinkList.Node<E> node(int i) {
        LinkList.Node<E> var2;
        int var1;
        if (i < this.size >> 1) {
            var2 = this.first;
            for (var1 = 0; var1 < i; ++var1) {
                var2 = var2.next;
            }
            return var2;
        } else {
            var2 = this.last;
            for (var1 = this.size - 1; var1 > i; --var1) {
                var2 = var2.next;
            }
            return var2;
        }
    }

    private static class Node<E> {
        E item;
        LinkList.Node<E> prev;
        LinkList.Node<E> next;

        Node(LinkList.Node<E> var1, E var2, LinkList.Node<E> var3) {
            prev = var1;
            item = var2;
            next = var3;
        }
    }

    public static void main(String[] args) {
        LinkList<String> linkList = new LinkList();
        linkList.add("11");
        linkList.add("22");

        String s = linkList.get(1);
        System.out.println(s);

    }

}