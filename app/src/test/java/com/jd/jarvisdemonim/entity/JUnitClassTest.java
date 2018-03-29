package com.jd.jarvisdemonim.entity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/29 0029
 * Name:
 * OverView:
 * Usage:
 */
public class JUnitClassTest {
    JUnitClass mJu;

    @Before
    public void setUp() throws Exception {
        mJu = new JUnitClass();
    }

    @After
    public void tearDown() throws Exception {
        mJu = null;
    }

    @Test
    public void sum() throws Exception {
        assertEquals(2, mJu.sum(1, 1));
    }

    @Test
    public void multiply() throws Exception {
        assertEquals(12, mJu.multiply(3, 4));
    }

}