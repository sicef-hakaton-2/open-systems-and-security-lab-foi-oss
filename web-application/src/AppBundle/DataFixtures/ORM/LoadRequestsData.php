<?php

namespace AppBundle\DataFixtures\ORM;

use AppBundle\Entity\citizen;
use AppBundle\Entity\Request;
use Zantolov\AppBundle\DataFixtures\ORM\AbstractDbFixture;

use Zantolov\AppBundle\Entity\User;

class LoadRequestsData extends AbstractDbFixture
{

    public static function getRandomLatLngNear($longitude, $latitude)
    {
//        $longitude = (float)18.695897;
//        $latitude = (float)45.554386;
        $radius = rand(5, 50); // in miles

        $lng_min = $longitude - $radius / abs(cos(deg2rad($latitude)) * 69);
        $lng_max = $longitude + $radius / abs(cos(deg2rad($latitude)) * 69);
        $lat_min = $latitude - ($radius / 69);
        $lat_max = $latitude + ($radius / 69);

        $decimals = 10000000000000000;

        return [
            'lat'  => rand($lat_min * $decimals, $lat_max * $decimals) / $decimals,
            'long' => rand($lng_min * $decimals, $lng_max * $decimals) / $decimals,
        ];
    }

    public function load(\Doctrine\Common\Persistence\ObjectManager $manager)
    {
        $faker = \Faker\Factory::create();

        for ($i = 1; $i <= 50; $i++) {
            $request = new Request();
            $request->setRefugee($this->getReference('refugee' . (rand(1, 50))));
            $request->setActive(true);

            $position = self::getRandomLatLngNear(21.903230, 43.321239);
            $request->setLatitude($position['lat']);
            $request->setLongitude($position['long']);
            $request->setTitle($faker->sentence());
            $request->setDescription($faker->realText());
            $manager->persist($request);
        }

        $manager->flush();
    }

    public function getOrder()
    {
        return 3;
    }

}
