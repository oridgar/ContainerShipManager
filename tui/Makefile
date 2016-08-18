'CC = gcc
TARGET = csaui
DESTDIR = /usr/local/bin

all: csconf

csconf:
	$(CC) -o $(TARGET) csconf.c -l ncurses -l panel -l menu

clean:
	$(RM) $(TARGET)

install: all
	cp $(TARGET) $(DESTDIR)/$(TARGET)

uninstall:
	rm -f $(DESTDIR)/$(TARGET)
