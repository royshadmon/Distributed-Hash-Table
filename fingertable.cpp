#include "node.h"

void FingerTable::set(size_t index, Node* successor) {
	fingerTable_[index] = successor;
}

void FingerTable::get(size_t index) {
	return fingerTable_[index];
}

void FingerTable::prettyprint() {

};
