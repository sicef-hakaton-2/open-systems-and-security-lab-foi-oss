<?php

namespace AppBundle\DataFixtures\ORM;

use AppBundle\Entity\citizen;
use Zantolov\AppBundle\DataFixtures\ORM\AbstractDbFixture;

use Zantolov\AppBundle\Entity\User;

class LoadCitizensData extends AbstractDbFixture
{

    public function load(\Doctrine\Common\Persistence\ObjectManager $manager)
    {
        $faker = \Faker\Factory::create();
        $userManager = $this->container->get('fos_user.user_manager');

        for ($i = 1; $i <= 50; $i++) {

            /** @var User $user */
            $user = $userManager->createUser();
            $user->setUsername('citizen' . $i);
            $user->setEmail('citizen' . $i . '@mailinator.com');
            $user->setPlainPassword('123456');
            $user->setEnabled(true);
            $user->setRoles(array('ROLE_USER', 'ROLE_CITIZEN'));
            $this->addReference('citizen' . $i, $user);
            $userManager->updateUser($user, true);


            $citizen = new Citizen();
            $citizen->setUser($user);
            $citizen->setActive(true);
            $citizen->setFirstName($faker->firstName);
            $citizen->setLastName($faker->lastName);
            $citizen->setCountry($faker->country);
            $manager->persist($citizen);
            $this->setReference('citizen' . $i, $citizen);


        }

        $manager->flush();
    }

    public function getOrder()
    {
        return 2;
    }

}
