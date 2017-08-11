//package com.nash.teacher.samples.crud;
//
//import java.io.Serializable;
//
//import com.nash.teacher.MyUI;
//import com.nash.teacher.backend.DataService;
//import com.nash.teacher.backend.data.Book;
//import com.vaadin.server.Page;
//
///**
// * This class provides an interface for the logical operations between the CRUD
// * view, its parts like the product editor form and the data source, including
// * fetching and saving products.
// *
// * Having this separate from the view makes it easier to test various parts of
// * the system separately, and to e.g. provide alternative views for the same
// * data.
// */
//public class SampleCrudLogic implements Serializable {
//
//    private SampleCrudView view;
//
//    public SampleCrudLogic(SampleCrudView simpleCrudView) {
//        view = simpleCrudView;
//    }
//
//    public void init() {
//        editBook(null);
//        // Hide and disable if not admin
//        if (!MyUI.get().getAccessControl().isUserInRole("admin")) {
//            view.setNewProductEnabled(false);
//        }
//    }
//
//    public void cancelProduct() {
//        setFragmentParameter("");
//        view.clearSelection();
//    }
//
//    /**
//     * Update the fragment without causing navigator to change view
//     */
//    private void setFragmentParameter(String productId) {
//        String fragmentParameter;
//        if (productId == null || productId.isEmpty()) {
//            fragmentParameter = "";
//        } else {
//            fragmentParameter = productId;
//        }
//
//        Page page = MyUI.get().getPage();
//        page.setUriFragment(
//                "!" + SampleCrudView.VIEW_NAME + "/" + fragmentParameter,
//                false);
//    }
//
//    public void enter(String productId) {
//        if (productId != null && !productId.isEmpty()) {
//            if (productId.equals("new")) {
//                newBook();
//            } else {
//                // Ensure this is selected even if coming directly here from
//                // login
//                try {
//                    int pid = Integer.parseInt(productId);
//                    Book product = findProduct(pid);
//                    view.selectRow(product);
//                } catch (NumberFormatException e) {
//                }
//            }
//        }
//    }
//
//    private Book findProduct(int productId) {
//        return DataService.get().getBook(productId);
//    }
//
//    public void saveBook(Book product) {
//        view.showSaveNotification(product.getTitle() + " ("
//                + product.getId() + ") updated");
//        view.clearSelection();
//        view.updateBook(product);
//        setFragmentParameter("");
//    }
//
//    public void deleteBook(Book product) {
//        view.showSaveNotification(product.getTitle() + " ("
//                + product.getId() + ") removed");
//        view.clearSelection();
//        view.removeBook(product);
//        setFragmentParameter("");
//    }
//
//    public void editBook(Book product) {
//        if (product == null) {
//            setFragmentParameter("");
//        } else {
//            setFragmentParameter(product.getId() + "");
//        }
//        view.editBook(product);
//    }
//
//    public void newBook() {
//        view.clearSelection();
//        setFragmentParameter("new");
//        view.editBook(new Book());
//    }
//
//    public void rowSelected(Book product) {
//        if (MyUI.get().getAccessControl().isUserInRole("admin")) {
//            view.editBook(product);
//        }
//    }
//}
