#include <ncurses.h>
#include <menu.h>
#include <panel.h>
#include <stdlib.h>
#include <string.h>


//Definitions
void print_footer(WINDOW *win, char *text);
void login_screen(WINDOW *win);
WINDOW* create_menu(void (*screenPtr)(WINDOW *),int posy, int posx);


void init() {
	initscr();
	cbreak();
	noecho();
	keypad(stdscr, TRUE);
	start_color();
	init_pair(1, COLOR_BLACK, COLOR_YELLOW); //define color pair 1
	init_pair(2, COLOR_WHITE, COLOR_BLACK);
	init_pair(3, COLOR_WHITE, COLOR_BLUE);
	curs_set(0); //Disable cursor blinking and hide it
	//attron(COLOR_PAIR(1));
}

int main() {
	//####################
	//Variable Declaration
	//####################
	WINDOW *my_win;
	WINDOW *my_winbottom;
	WINDOW *login_win;
	WINDOW *cur_win;
	PANEL  *my_panels[3];
	PANEL  *winbottom_panel;

	int c;
	int i;
	int row,col;
	int main_row,main_col,login_row,login_col;
	char *title = "Container Ship Agent";
	void (*functionPtr)(WINDOW *);

	//############
	//Code Section
	//############
	init();
	getmaxyx(stdscr,row,col);

	//Title
	//attron(A_BOLD);
	mvprintw(0,col/2 - strlen(title)/2, title);
	refresh();
	//attroff(A_BOLD);

	//Main window colored
	main_row = row - 2;
	main_col = col - 2;
	my_win = newwin(main_row / 2, main_col, 1, 1);
	wbkgd(my_win, COLOR_PAIR(1));
	box(my_win, 0, 0);
	//wborder(my_win, '|', '|', '-', ' ', ' ', ' ', ' ', ' ');
	my_panels[0] = new_panel(my_win);
	wrefresh(my_win);

	my_winbottom = newwin(main_row / 2, main_col, 1 + main_row /2, 1);
	wbkgd(my_winbottom, COLOR_PAIR(3));
	box(my_winbottom, 0, 0);
	winbottom_panel = new_panel(my_winbottom);
	wrefresh(my_winbottom);
	//Main window on top
	update_panels();
	doupdate();

	//Main window loop
	do {
		//Footer
		print_footer(stdscr,"<F1> Login <ESC> Exit");
		c = getch();
		switch (c) {

		case KEY_F(1):
			functionPtr = &login_screen;
			cur_win = create_menu(functionPtr,
				row - main_row + (main_row / 2) - (login_row / 2) -1, 
				col - main_col + (main_col / 2) - (login_col / 2) -1);

			//Putting main window on top
			top_panel(my_panels[0]);
			top_panel(winbottom_panel);
			update_panels();
			doupdate();


			//DONT KNOW WHAT'S DOING
			//werase(login_win);
			//delwin(login_win);
			delwin(cur_win);
			break;
		} //case
	} while (c != 27);
	//move(3,3); //move the cursor to position on window

	//Finalize
	delwin(my_win);
	endwin();
	return 0;
}

void print_menu(WINDOW *menu_win, int highlight, char **in_choices, int n_choices) {
	//####################
	//Variable Declaration
	//####################
	int x, y, i,row,col;


	getmaxyx(menu_win,row,col);
	x = 1;
	y = 1;
	for (i = 0; i < n_choices; i++) {
		if (highlight == i + 1) {
			wattron(menu_win, A_REVERSE);
			mvwprintw(menu_win, y, x, "%s", in_choices[i]);
			wattroff(menu_win, A_REVERSE);
		}
		else
			mvwprintw(menu_win, y, x, "%s", in_choices[i]);
		y++;
	}
	wrefresh(menu_win);
}

void print_footer(WINDOW *win, char *text) {
	//####################
	//Variable Declaration
	//####################
	int col,row;

	//############
	//Code Section
	//############
	getmaxyx(stdscr,row,col);

	//clean line
	wmove(win, row - 1, 0);
	wclrtoeol(win);

	//Print footer
	mvwprintw(win, row - 1 , 0, text);
	wrefresh(win);
}

WINDOW* create_menu(void (*screenPtr)(WINDOW *),int posy, int posx) {
	WINDOW *win;
	PANEL  *panel;
	int row, col;

	//Login window
	row = 10;
	col = 40;
	win = newwin(row, col, 10, 10);
	box(win, 0, 0);
	wbkgd(win, COLOR_PAIR(2));
	wrefresh(win);
	keypad(win, TRUE);
	panel = new_panel(win);
	top_panel(panel);
	update_panels();
	doupdate();
	//Calling to the right screen
	(*screenPtr)(win);
	keypad(win, FALSE);
	//delwin(win);
	return win;
}

void login_screen(WINDOW *win) {
	int menu_result;
	char *choices[] = {
		"Configure Network",
		"Reboot"
	};
	int n_choices = sizeof(choices) / sizeof(char *);
	//ITEM **item_list;
	//MENU *menu;

	//Menu Library

	/*
	item_list = (ITEM **)calloc(n_choices + 1, sizeof(ITEM *));
	for(i = 0; i < n_choices; i++)
		item_list[i] = new_item(choices[i], choices[i]);
	item_list[n_choices] = (ITEM *)NULL;

	menu = new_menu((ITEM **)item_list);
	*/

	while ((menu_result = select_menu(win, choices, n_choices)) != 0) {
		if (menu_result == 1) {
		//network_screen(menu_win);
		}
	}
}

void network_screen(WINDOW *win) {
	int menu_result;
	char *choices[] = {
		"Configure IP",
		"Restart Network Interfaces"
	};
	int n_choices = sizeof(choices) / sizeof(char *);

	menu_result = select_menu(win, choices, n_choices);
}

int select_menu(WINDOW *menu_win, char **in_choices, int n_choices) {
	int highlight = 1;
	int c;

	do {
		print_footer(stdscr,"<Enter> Choice <ESC> Exit");
		print_menu(menu_win, highlight, in_choices, n_choices);
		c = getch();
		switch (c) {
			case KEY_UP:
				if(highlight == 1)
					highlight = n_choices;
				else
					--highlight;
				break;
			case KEY_DOWN:
				if(highlight == n_choices)
					highlight = 1;
				else
					++highlight;

				break;
			case KEY_HOME:
				highlight = 1;
				break;
			case KEY_END:
				highlight = n_choices;
				break;
			case 10: // Enter or Return
				//Temporary to perform action with Enter
				return highlight;
				break;
		}
	}while (c != 27);
	return 0;
}
