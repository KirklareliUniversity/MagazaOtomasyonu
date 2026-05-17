/* Ana Arkaplan */
.root {
    -fx-background-color: #f4f7f6;
}

/* Kart Tasarımı (İstatistikler için) */
.stat-card {
    -fx-background-color: white;
    -fx-background-radius: 15;
    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);
    -fx-padding: 20;
}

/* Modern Butonlar */
.button {
    -fx-background-radius: 8;
    -fx-cursor: hand;
    -fx-transition: 0.3s;
}
.button:hover {
    -fx-opacity: 0.8;
    -fx-scale-x: 1.05;
    -fx-scale-y: 1.05;
}

/* Tabloyu Güzelleştirme */
.table-view {
    -fx-background-radius: 10;
    -fx-border-radius: 10;
    -fx-background-color: transparent;
}
.table-view .column-header {
    -fx-background-color: #2c3e50;
}
.table-view .column-header .label {
    -fx-text-fill: white;
    -fx-font-weight: bold;
}