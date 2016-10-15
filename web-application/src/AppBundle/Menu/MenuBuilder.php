<?php

namespace AppBundle\Menu;

use Doctrine\Common\Collections\ArrayCollection;
use Knp\Menu\FactoryInterface;
use Symfony\Component\HttpFoundation\RequestStack;
use Zantolov\AppBundle\Menu\MenuBuilderInterface;

class MenuBuilder implements MenuBuilderInterface
{

    private $factory;

    /**
     * @param FactoryInterface $factory
     */
    public function __construct(FactoryInterface $factory)
    {
        $this->factory = $factory;
    }

    public function createMenu(RequestStack $requestStack)
    {
        $menuItems = array();

        $menuItems['people'] = $this->factory->createItem('people', array('label' => 'People'))
            ->setAttribute('dropdown', true)
            ->setAttribute('icon', 'fa fa-users');

        $menuItems['people']->addChild('refugees', array('label' => 'Refugees', 'route' => 'admin_refugees.index'))->setAttribute('icon', 'fa fa-users');
        $menuItems['people']->addChild('authorities', array('label' => 'Authorities', 'route' => 'admin_authorities.index'))->setAttribute('icon', 'fa fa-users');
        $menuItems['people']->addChild('citizens', array('label' => 'Citizens', 'route' => 'admin_citizens.index'))->setAttribute('icon', 'fa fa-users');

        $menuItems['requestsoffers'] = $this->factory->createItem('requestsoffers', array('label' => 'Request/Offer'))
            ->setAttribute('dropdown', true)
            ->setAttribute('icon', 'fa fa-plus');

        $menuItems['requestsoffers']->addChild('requests', array('label' => 'Requests', 'route' => 'admin_request.index'))->setAttribute('icon', 'fa fa-minus');
        $menuItems['requestsoffers']->addChild('offers', array('label' => 'Offers', 'route' => 'admin_offer.index'))->setAttribute('icon', 'fa fa-plus');


//        $menuItems['notif'] = $this->factory->createItem('notif', array('label' => 'Push Notification', 'route' => 'admin_push_notification'))->setAttribute('icon', 'fa fa-exclamation-triangle');

        return $menuItems;
    }


    public function getOrder()
    {
        return 5;
    }
}