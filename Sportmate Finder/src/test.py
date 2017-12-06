#!/usr/bin/env python2.7

import sqlite3
from Base import *

print "\n--- CREATION OBJET BASE ---"
base = Base()
print "\n--- INITIALISATION DE LA BASE ---"
base.reset()
base.printAll()
