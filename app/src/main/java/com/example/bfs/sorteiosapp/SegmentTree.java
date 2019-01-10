package com.example.bfs.sorteiosapp;

public class SegmentTree {

    private int tree[];

    SegmentTree(int n) {
        tree = new int[4*n];
        for(int i = 0; i < 4*n; i++)
            tree[i] = 0;
    }

    int update(int l, int r, int idx, int v, int pos) {

        if(l > r || l > idx || r < idx) {
            return tree[pos];
        }

        if(l == r && l == idx) {
            return tree[pos] = v;
        }

        int mid = (l+r)/2;
        return tree[pos] = update(l, mid, idx, v, 2*pos+1) + update(mid + 1, r, idx, v, 2*pos+2);
    }

    int getID(int l, int r, int pref, int v, int pos) {

        if(l == r) {
            return l;
        }

        int mid = (l+r)/2;
        int a = tree[2*pos+1];
        if(v < pref + a && a > 0) {
            return getID(l, mid, pref, v, 2*pos+1);
        } else {
            return getID(mid+1, r, pref + a, v, 2*pos+2);
        }

    }
}
