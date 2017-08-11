//package com.nash.teacher.samples.crud;
//
//import java.text.DecimalFormat;
//import java.util.Comparator;
//import java.util.stream.Collectors;
//
//import com.nash.teacher.backend.data.Availability;
//import com.nash.teacher.backend.data.Book;
//import com.nash.teacher.backend.data.Checkout;
//import com.vaadin.icons.VaadinIcons;
//import com.vaadin.ui.Grid;
//import com.vaadin.ui.renderers.HtmlRenderer;
//import com.vaadin.ui.renderers.NumberRenderer;
//
///**
// * Grid of products, handling the visual presentation and filtering of a set of
// * items. This version uses an in-memory data source that is suitable for small
// * data sets.
// */
//public class ProductGrid extends Grid<Book> {
//
//    public ProductGrid() {
//        setSizeFull();
//
//        addColumn(Book::getId, new NumberRenderer()).setCaption("Id");
//        addColumn(Book::getTitle).setCaption("Product Name");
//
//        // Format and add " €" to price
//        final DecimalFormat decimalFormat = new DecimalFormat();
//        decimalFormat.setMaximumFractionDigits(2);
//        decimalFormat.setMinimumFractionDigits(2);
//
//        // Add an traffic light icon in front of availability
//        addColumn(this::htmlFormatAvailability, new HtmlRenderer())
//                .setCaption("Availability").setComparator((p1, p2) -> {
//                    return p1.getAvailability().toString()
//                            .compareTo(p2.getAvailability().toString());
//                });
//
//        // Show empty stock as "-"
//        addColumn(product -> {
//            if (product.getStockCount() == 0) {
//                return "-";
//            }
//            return Integer.toString(product.getStockCount());
//        }).setCaption("Stock Count").setComparator((p1, p2) -> {
//            return Integer.compare(p1.getStockCount(), p2.getStockCount());
//        }).setStyleGenerator(product -> "align-right");
//    }
//
//    public Book getSelectedRow() {
//        return asSingleSelect().getValue();
//    }
//
//    public void refresh(Book product) {
//        getDataCommunicator().refresh(product);
//    }
//
//    private String htmlFormatAvailability(Book product) {
//        Availability availability = product.getAvailability();
//        String text = availability.toString();
//
//        String color = "";
//        switch (availability) {
//        case AVAILABLE:
//            color = "#2dd085";
//            break;
//        case UNAVAILABLE:
//            color = "#f54993";
//            break;
//        default:
//            break;
//        }
//
//        String iconCode = "<span class=\"v-icon\" style=\"font-family: "
//                + VaadinIcons.CIRCLE.getFontFamily() + ";color:" + color
//                + "\">&#x"
//                + Integer.toHexString(VaadinIcons.CIRCLE.getCodepoint())
//                + ";</span>";
//
//        return iconCode + " " + text;
//    }
//}
