<?php

namespace AppBundle\DataFixtures\ORM;

use AppBundle\Entity\citizen;
use AppBundle\Entity\Offer;
use Zantolov\AppBundle\DataFixtures\ORM\AbstractDbFixture;

use Zantolov\AppBundle\Entity\User;

class LoadOffersData extends AbstractDbFixture
{

    public function load(\Doctrine\Common\Persistence\ObjectManager $manager)
    {
        $faker = \Faker\Factory::create();

        for ($i = 1; $i <= 50; $i++) {
            $offer = new Offer();
            $offer->setCitizen($this->getReference('citizen' . (rand(1, 50))));
            $offer->setActive(true);

            $position = LoadRequestsData::getRandomLatLngNear(21.903230, 43.321239);
            $offer->setLatitude($position['lat']);
            $offer->setLongitude($position['long']);
            $offer->setTitle($faker->sentence());
            $offer->setDescription($faker->realText());
            $manager->persist($offer);
        }

        $manager->flush();
    }

    public function getOrder()
    {
        return 3;
    }

}
