package cart.domain;

import cart.domain.coupon.Coupon;
import java.math.BigDecimal;
import java.util.List;

public class Orders {

    private static final Long DEFAULT_DELIVERY_FEE = 3000L;
    private final Long id;
    private final Long deliveryFee;
    private final MemberCoupon memberCoupon;
    private final Member member;

    private final List<OrderItem> orderItems;

    public Orders(final MemberCoupon memberCoupon, final Member member, final List<OrderItem> orderItems) {
        this(null, DEFAULT_DELIVERY_FEE, memberCoupon, member, orderItems);
    }

    public Orders(final Long id, final Long deliveryFee, final MemberCoupon memberCoupon, final Member member,
                  final List<OrderItem> orderItems) {
        this.id = id;
        this.deliveryFee = deliveryFee;
        this.memberCoupon = memberCoupon;
        this.member = member;
        this.orderItems = orderItems;
    }

    public BigDecimal getCalculateDiscountPrice() {
        long totalPrice = getTotalPrice();
        return memberCoupon.discountPrice(totalPrice);
    }

    private long getTotalPrice() {
        return orderItems.stream()
                .mapToLong(OrderItem::getCalculatePrice)
                .sum();
    }

    public Long getId() {
        return id;
    }

    public Long getDeliveryFee() {
        return deliveryFee;
    }

    public MemberCoupon getMemberCoupon() {
        return memberCoupon;
    }

    public Coupon getCoupon() {
        return memberCoupon.getCoupon();
    }

    public Member getMember() {
        return member;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public BigDecimal getDiscountPrice() {
        return BigDecimal.valueOf(getTotalPrice()).subtract(getCalculateDiscountPrice());
    }
}
