/*
 * Copyright 2008 Adrian Buerki
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.gwt.gwtai.demo.impl;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;

import javafx.scene.effect.Reflection;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.Interpolator;

var o1:Number;

var t = Timeline {
    repeatCount: Timeline.INDEFINITE
    autoReverse: true

    keyFrames: [
        KeyFrame{
            time: 0s
            values: o1 => 0.3
        },
        KeyFrame{
            time: 3.5s
            values: o1 => 1.0 tween Interpolator.EASEBOTH
        }
    ]
}

t.play();

Stage {
    title: "GwtAI FX integration Demo"
    width: 320
    height: 230
    scene: Scene {
        fill: Color.BLACK
        content: [
            Group {
                content: [
                    Rectangle {
                        x: 20.0
                        y: 20.0
                        fill: Color.WHITESMOKE
                        width: 280
                        height: 80
                        arcHeight: 15
                        arcWidth: 15
                        stroke: Color.ORANGE
                        strokeWidth: 3
                    },
                    Text {
                        content: 'JavaFX rocks!'
                        font: Font {
                            name: 'Verdana',
                            embolden: true,
                            size: 30
                        }
                        fill: Color.ORANGE
                        x: 55.0
                        y: 75.0
                    }]
                effect: Reflection {
                        fraction: 0.50
                        topOpacity: 0.8
                    }
                opacity: bind o1
            },
            Text {
                content: 'GWT and JavaFX => http://code.google.com/p/gwtai/'
                font: Font {
                    name: 'Verdana',
                    size: 9
                    oblique: true
                }
                fill: Color.WHITESMOKE
                x: 20.0
                y: 175.0
            }
        ]
    }
}