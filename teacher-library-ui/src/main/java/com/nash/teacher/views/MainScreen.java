package com.nash.teacher.views;

import com.nash.teacher.MyUI;
import com.nash.teacher.views.books.BooksView;
import com.nash.teacher.views.checkouts.CheckoutsView;
import com.nash.teacher.views.dashboard.DashboardView;
import com.nash.teacher.views.students.StudentsView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

/**
 * Content of the UI when the user is logged in.
 * 
 * 
 */
public class MainScreen extends HorizontalLayout {

	private static final long serialVersionUID = 1L;

	private Menu menu;

	public MainScreen(MyUI ui) {

		setSpacing(false);
		setStyleName("main-screen");

		CssLayout viewContainer = new CssLayout();
		viewContainer.addStyleName("valo-content");
		viewContainer.setSizeFull();

		final Navigator navigator = new Navigator(ui, viewContainer);
		navigator.setErrorView(ErrorView.class);
		menu = new Menu(navigator);
		menu.addView(new DashboardView(), DashboardView.VIEW_NAME, DashboardView.VIEW_NAME, VaadinIcons.DASHBOARD);
		menu.addView(new BooksView(), BooksView.VIEW_NAME, BooksView.VIEW_NAME, VaadinIcons.BOOK);
		menu.addView(new StudentsView(), StudentsView.VIEW_NAME, StudentsView.VIEW_NAME, VaadinIcons.ACADEMY_CAP);
		menu.addView(new CheckoutsView(), CheckoutsView.VIEW_NAME, CheckoutsView.VIEW_NAME, VaadinIcons.CHECK);

		navigator.addViewChangeListener(viewChangeListener);

		addComponent(menu);
		addComponent(viewContainer);
		setExpandRatio(viewContainer, 1);
		setSizeFull();
	}

	// notify the view menu about view changes so that it can display which view
	// is currently active
	ViewChangeListener viewChangeListener = new ViewChangeListener() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public boolean beforeViewChange(ViewChangeEvent event) {
			return true;
		}

		@Override
		public void afterViewChange(ViewChangeEvent event) {
			menu.setActiveView(event.getViewName());
		}

	};
}
