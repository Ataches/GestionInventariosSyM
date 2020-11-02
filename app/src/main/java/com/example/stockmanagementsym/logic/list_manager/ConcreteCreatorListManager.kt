package com.example.stockmanagementsym.logic.list_manager

/*
    Created by Juan Sebastian Sanchez Mancilla on 1/11/2020
*/
class ConcreteCreatorListManager:CreatorListManager() {
    override fun factoryMethod(): IListManager {
        return ListManager()
    }
}