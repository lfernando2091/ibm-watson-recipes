/*
 * Copyright Lyo(c) 2017
 *  Autorized Luis Fernando Hernández Méndez
 * signature
 * b56a75f4cd38f40f3a0f4ded26e3902fe8f172594c9d9fafe889a2f4f8f5145a
 */

package com.media.doopy.Post;

public interface PostListener {
    void onConnectionStarts();
    void onDataReceived(String s);
}
