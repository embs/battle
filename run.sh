#!/usr/bin/env bash

scalac -cp "*.jar:." Battle.scala
scala -cp "*.jar:." Battle
