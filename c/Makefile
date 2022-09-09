CC = cl
CFLAGS = /Zi /EHsc /nologo

LIB_DIR = "C:/Program Files (x86)/Windows Kits/10/Lib/10.0.19041.0/um/x86/"
LIBS = user32.lib
LIBS := $(addprefix $(LIB_DIR), $(LIBS))

SRC_DIR = src\\
SRC = main.cpp mouse.cpp window.cpp
SRC := $(addprefix $(SRC_DIR), $(SRC))

HEADER_DIR = headers

OBJ = $(SRC:.cpp=.obj)


.PHONY: all clean
.SUFFIXES: .cpp .obj

all: remote-control.exe

remote-control.exe: $(OBJ)
	$(CC) $(CFLAGS) /Fe$@ $^ $(LIBS)

.cpp.obj:
	$(CC) $(CFLAGS) /I$(HEADER_DIR) /c $< /Fo$@

clean:
	del /F /Q *.exe *.pdb *.ilk *.manifest
	del /F $(OBJ)