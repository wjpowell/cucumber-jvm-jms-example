package cucumber.examples.java.jms;

import java.io.Serializable;

public class Trade implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double price;
	private String buyer;
	private String Seller;
	private String quantity;
	private String product;
	private boolean executed;

	public boolean isExecuted() {
		return executed;
	}

	public void setExecuted(boolean executed) {
		this.executed = executed;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getSeller() {
		return Seller;
	}

	public void setSeller(String seller) {
		Seller = seller;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "Trade [price=" + price + ", buyer=" + buyer + ", Seller="
				+ Seller + ", quantity=" + quantity + ", product=" + product
				+ ", executed=" + executed + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Seller == null) ? 0 : Seller.hashCode());
		result = prime * result + ((buyer == null) ? 0 : buyer.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result
				+ ((quantity == null) ? 0 : quantity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Trade)) {
			return false;
		}
		Trade other = (Trade) obj;
		if (Seller == null) {
			if (other.Seller != null) {
				return false;
			}
		} else if (!Seller.equals(other.Seller)) {
			return false;
		}
		if (buyer == null) {
			if (other.buyer != null) {
				return false;
			}
		} else if (!buyer.equals(other.buyer)) {
			return false;
		}
		if (Double.doubleToLongBits(price) != Double
				.doubleToLongBits(other.price)) {
			return false;
		}
		if (product == null) {
			if (other.product != null) {
				return false;
			}
		} else if (!product.equals(other.product)) {
			return false;
		}
		if (quantity == null) {
			if (other.quantity != null) {
				return false;
			}
		} else if (!quantity.equals(other.quantity)) {
			return false;
		}
		return true;
	}
}