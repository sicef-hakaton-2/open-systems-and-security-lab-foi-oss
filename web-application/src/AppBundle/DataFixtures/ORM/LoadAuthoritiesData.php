<?php

namespace AppBundle\DataFixtures\ORM;

use AppBundle\Entity\authority;
use Zantolov\AppBundle\DataFixtures\ORM\AbstractDbFixture;

use Zantolov\AppBundle\Entity\User;

class LoadAuthoritiesData extends AbstractDbFixture
{

    public function load(\Doctrine\Common\Persistence\ObjectManager $manager)
    {
        $faker = \Faker\Factory::create();
        $userManager = $this->container->get('fos_user.user_manager');

        for ($i = 1; $i <= 50; $i++) {

            /** @var User $user */
            $user = $userManager->createUser();
            $user->setUsername('authority' . $i);
            $user->setEmail('authority' . $i . '@mailinator.com');
            $user->setPlainPassword('123456');
            $user->setEnabled(true);
            $user->setRoles(array('ROLE_USER', 'ROLE_AUTHORITY'));
            $this->addReference('authority' . $i, $user);
            $userManager->updateUser($user, true);

            $authority = new Authority();
            $authority->setUser($user);
            $authority->setActive(true);
            $authority->setFirstName($faker->firstName);
            $authority->setLastName($faker->lastName);
            $authority->setCountry($faker->country);
            $manager->persist($authority);

        }

        $manager->flush();
    }

    public function getOrder()
    {
        return 2;
    }

}
